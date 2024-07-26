pluginManagement {
    repositories {
        maven("https://maven.fabricmc.net/") {
            name = "Fabric"
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "arte"
include("common")
include("bukkit")
include("fabric-common")

include("fabric")
include("fabric:1.20.5")
include("fabric:1.20.3")