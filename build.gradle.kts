// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        // Add the Google Services classpath
        classpath("com.google.gms:google-services:4.4.2")
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.google.gms.google.services) apply false
}
//
//allprojects {
//    repositories {
//        google()
//        mavenCentral()
//    }
//}
//
//tasks.register("clean", Delete::class) {
//    delete(rootProject.layout.buildDirectory)
//}


