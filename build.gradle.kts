// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    // The id for the Android application plugin
    id("com.android.application") version "8.7.2" apply false
    // The id for the Kotlin plugin
    id("org.jetbrains.kotlin.jvm") version "1.8.0" apply false
}
buildscript {
    repositories {
        google()
        mavenCentral() // Required for Retrofit and other dependencies
    }
}
