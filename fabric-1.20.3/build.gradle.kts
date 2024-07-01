plugins {
    id("fabric-loom")
}

val minecraftVersion: String by project
val yarnMappings: String by project
val loaderVersion: String by project
val fabricVersion: String by project

dependencies {
    // To change the versions see the gradle.properties file
    minecraft("com.mojang:minecraft:${minecraftVersion}")
    mappings("net.fabricmc:yarn:${yarnMappings}:v2")
    modImplementation("net.fabricmc:fabric-loader:${loaderVersion}")

    // Fabric API. This is technically optional, but you probably want it anyway.
    modImplementation("net.fabricmc.fabric-api:fabric-api:${fabricVersion}")

    // common and its dependencies
    implementation(project(":common"))
    include(project(":common"))

    implementation("org.json:json:20231013")
    include("org.json:json:20231013")

    include("org.apache.commons:commons-compress:1.21")
    include("commons-codec:commons-codec:1.16.0")
}

tasks.processResources {
    inputs.property("version", project.version)
    filesMatching("fabric.mod.json") {
        expand(getProperties())
        expand(mutableMapOf("version" to project.version))
    }
}

tasks.withType<AbstractArchiveTask> {
    setProperty("archiveFileName", "arte-fabric-${rootProject.version}-1.20.3-1.20.4.jar")
}