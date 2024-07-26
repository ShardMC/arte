plugins {
    id("fabric-loom") version "1.7-SNAPSHOT" apply false
}

subprojects.forEach {
    it.apply(from = "${project.projectDir}/mod.gradle")
}