plugins {
    id("java")
}

group = "dev.drtheo"
version = "0.2.0"

dependencies {
    compileOnly("org.apache.commons:commons-compress:1.21")
    compileOnly("commons-codec:commons-codec:1.16.0")
}