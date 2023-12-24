pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://maven.fabricmc.net/")
    }
}

rootProject.name = "arte"
include("common")
include("bukkit")
include("fabric")
include("headless")
