@file:Suppress("UnstableApiUsage")

import dimension.Dimensions
import version.BuildVersions
import version.addVersionsToBuildConfig

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdk = BuildVersions.COMPILE_SDK_VERSION

    defaultConfig {
        minSdk = BuildVersions.MIN_SDK_VERSION
        targetSdk = BuildVersions.TARGET_SDK_VERSION

        addVersionsToBuildConfig()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName(Dimensions.BuildType.Release.NAME) {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = BuildVersions.JAVA_VERSION
        targetCompatibility = BuildVersions.JAVA_VERSION
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = BuildVersions.JAVA_VERSION.toString()
        }
    }

    lint {
        isAbortOnError = false
        isCheckReleaseBuilds = false
    }

    kapt {
        correctErrorTypes = true
    }

    testOptions {
        unitTests {
            isReturnDefaultValues = true
        }
    }
}
