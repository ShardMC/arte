plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "the.grid.smp.arte"

repositories {
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.20.3-R0.1-SNAPSHOT")
    implementation(project(":common"))
}

tasks.shadowJar {
    dependencies {
        exclude(dependency("org.apache.commons:commons-compress:1.21"))
        exclude(dependency("commons-codec:commons-codec:1.16.0"))

        //bound to spigot!
        exclude(dependency("commons-io:commons-io:2.13.0"))
    }
}

tasks.build {
    dependsOn(tasks.shadowJar)
}

tasks.processResources {
    val props = mapOf("version" to rootProject.version)
    inputs.properties(props)

    filesMatching("plugin.yml") {
        expand(props)
    }
}

tasks.withType<AbstractArchiveTask> {
    setProperty("archiveFileName", "arte-bukkit-${rootProject.version}.jar")
}