plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "the.grid.smp.arte"

repositories {
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://maven.pkg.github.com/TheGridSMP/communis") {
        credentials {
            username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
            password = project.findProperty("gpr.key") as String? ?: System.getenv("TOKEN")
        }
    }
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.20.3-R0.1-SNAPSHOT")
    compileOnly("the.grid.smp:communis:1.6.1")

    compileOnly("org.apache.commons:commons-compress:1.21")
    compileOnly("commons-codec:commons-codec:1.16.0")

    implementation(project(":common"))
}

tasks.build {
    dependsOn(tasks.shadowJar)
}

tasks.processResources {
    val props = mapOf("version" to version)
    inputs.properties(props)

    filesMatching("plugin.yml") {
        expand(props)
    }
}

tasks.withType<AbstractArchiveTask> {
    setProperty("archiveFileName", "arte-bukkit-${rootProject.version}.jar")
}