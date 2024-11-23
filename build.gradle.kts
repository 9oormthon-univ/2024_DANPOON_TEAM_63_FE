// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    kotlin("jvm") version "2.0.20"
    kotlin("plugin.serialization") version "2.0.20"
//    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.10" // https://github.com/Kotlin/kotlinx.serialization?tab=readme-ov-file#gradle
}