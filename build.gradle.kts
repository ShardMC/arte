subprojects {
    group = "su.shardmc.arte"

    repositories {
        mavenCentral()
    }

    val common by configurations.creating
}

val version: String by project