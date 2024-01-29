dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "com.example.hastanakiluygulamasi2"
include(":app")

// I moved the plugins block from the build.gradle.kts (project) file to here
// This will make Gradle synchronize faster
plugins {
}
