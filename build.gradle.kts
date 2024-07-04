subprojects {
    group = "io.shardmc.arte"

    repositories {
        mavenCentral()
    }

    val common by configurations.creating
}

val version: String by project