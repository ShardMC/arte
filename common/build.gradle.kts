plugins {
    id("java")
}

group = "dev.drtheo"
version = "0.2.0"

dependencies {
    implementation("org.apache.commons:commons-compress:1.21")
    implementation("commons-codec:commons-codec:1.16.0")

    // bound to spigot!
    implementation("commons-io:commons-io:2.13.0")
}