plugins {
    id("fabric-loom") version "1.2-SNAPSHOT"
}

group = "the.grid.smp.arte"

/* I don't know how to do it without having to shit project.getProperty("name") everywhere in kotlin dsl... */
val minecraftVersion = "1.20.4"
val yarnMappings = "1.20.4+build.3"
val loaderVersion = "0.15.3"
val apiVersion = "0.91.3+1.20.4"

dependencies {
    // To change the versions see the gradle.properties file
    minecraft("com.mojang:minecraft:${minecraftVersion}")
    mappings("net.fabricmc:yarn:${yarnMappings}:v2")
    modImplementation("net.fabricmc:fabric-loader:${loaderVersion}")

    // Fabric API. This is technically optional, but you probably want it anyway.
    modImplementation("net.fabricmc.fabric-api:fabric-api:${apiVersion}")

    include(project(":common")) // common and its dependencies
    include("org.apache.commons:commons-compress:1.21")
    include("commons-codec:commons-codec:1.16.0")
}

tasks.processResources {
    val props = mapOf("version" to rootProject.version,
            "minecraft_version" to minecraftVersion,
            "loader_version" to loaderVersion
    )

    inputs.properties(props)
    filesMatching("fabric.mod.json") {
        expand(props)
    }
}

tasks.withType<AbstractArchiveTask> {
    setProperty("archiveFileName", "arte-fabric-${rootProject.version}.jar")
}