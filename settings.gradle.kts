pluginManagement {
    repositories {
        maven("https://maven.fabricmc.net/") {
            name = "Fabric"
        }
        mavenCentral()
        gradlePluginPortal()
    }

    val loomVersion: String by settings
    plugins {
        id("fabric-loom") version loomVersion
    }
}

rootProject.name = "arte"
include("common")
include("bukkit")
include("fabric")
