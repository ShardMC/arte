plugins {
    id("java")
}

group = "the.grid.smp"
version = "0.1.0"

repositories {
    mavenCentral()
    maven("https://libraries.minecraft.net/")
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
}

tasks.withType<ProcessResources> {
    val props = mapOf("version" to version)
    inputs.properties(props)

    filesMatching("plugin.yml") {
        expand(props)
    }
}
