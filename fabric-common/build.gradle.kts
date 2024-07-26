plugins {
    id("fabric-loom") version "1.7-SNAPSHOT"
}

val minecraftVersion: String by project
val yarnMappings: String by project
val loaderVersion: String by project
val fabricVersion: String by project

dependencies {
    // To change the versions see the gradle.properties file
    minecraft("com.mojang:minecraft:${minecraftVersion}")
    mappings("net.fabricmc:yarn:${yarnMappings}:v2")
    modCompileOnly("net.fabricmc:fabric-loader:${loaderVersion}")

    // Fabric API. This is technically optional, but you probably want it anyway.
    modCompileOnly("net.fabricmc.fabric-api:fabric-api:${fabricVersion}")

    // common and its dependencies
    implementation(project(":common"))
    common(project(":common"))

    compileOnly("org.json:json:20231013")
    include("org.json:json:20231013")

    include("org.apache.commons:commons-compress:1.21")
    include("commons-codec:commons-codec:1.16.0")
}

tasks.processResources {
    inputs.property("version", project.version)
    inputs.property("minecraftVersion", minecraftVersion)

    filesMatching("fabric.mod.json") {
        expand(mutableMapOf(
            "version" to project.version,
            "minecraftVersion" to minecraftVersion
        ))
    }
}

tasks.withType<AbstractArchiveTask> {
    setProperty("archiveFileName", "arte-fabric-common.jar")
}

tasks.remapJar {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    from(configurations.getByName("common").map { if (it.isDirectory) it else zipTree(it) })
}