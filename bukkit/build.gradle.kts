plugins {
    id("java")
}

repositories {
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.20.3-R0.1-SNAPSHOT")
    compileOnly(files("libs/ProtocolLib.jar"))

    implementation(project(":common"))
    common(project(":common"))
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

    from(configurations.getByName("common").map { if (it.isDirectory) it else zipTree(it) })
    duplicatesStrategy = DuplicatesStrategy.WARN
}