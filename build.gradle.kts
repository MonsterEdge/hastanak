buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath ("com.android.tools.build:gradle:8.2.2")
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.22")
    }
}




// The buildscript block defines the classpaths for Android Studio to compile your Android applications
// I moved this block to the settings.gradle file to make Gradle synchronize faster

// The allprojects block defines the repositories for Gradle to download your dependencies
// I moved this block to the settings.gradle file to make Gradle synchronize faster


/*
plugins {
    id("org.jetbrains.kotlin.jvm") version "1.7.10"
}
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath ("com.android.tools.build:gradle:8.0.2")
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.10")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
*/