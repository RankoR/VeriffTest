import dependency.Dependencies
import dimension.Dimensions
import extension.addCoreTestLibraries
import version.BuildVersions
import version.addVersionsToBuildConfig

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdk = BuildVersions.COMPILE_SDK_VERSION

    defaultConfig {
        applicationId = "com.example.testapp"

        minSdk = BuildVersions.MIN_SDK_VERSION
        targetSdk = BuildVersions.TARGET_SDK_VERSION

        versionCode = BuildVersions.VERSION_CODE
        versionName = BuildVersions.versionName

        addVersionsToBuildConfig()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName(Dimensions.BuildType.Debug.NAME) {
            applicationIdSuffix = ".test"

            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }

        getByName(Dimensions.BuildType.Release.NAME) {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")

            // signingConfig = signingConfigs.getByName("release")
        }
    }

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = BuildVersions.JAVA_VERSION
        targetCompatibility = BuildVersions.JAVA_VERSION
    }

    kotlinOptions {
        jvmTarget = BuildVersions.JAVA_VERSION.toString()
    }

    kapt {
        correctErrorTypes = true
    }

    packagingOptions {
        resources.excludes.add("DebugProbesKt.bin")
    }

    lint {
        isCheckReleaseBuilds = false
    }

    testOptions {
        unitTests {
            isReturnDefaultValues = true
        }
    }
}

dependencies {
    implementation(projects.core)
    implementation(projects.sdk)

    // AndroidX
    implementation(Dependencies.Libraries.ANDROID_X_APPCOMPAT)
    implementation(Dependencies.Libraries.ANDROID_X_CONSTRAINT_LAYOUT)

    // Material
    implementation(Dependencies.Libraries.GOOGLE_MATERIAL)

    // CameraX
    implementation(Dependencies.Libraries.CAMERA_X)
    implementation(Dependencies.Libraries.CAMERA_X_LIFECYCLE)
    implementation(Dependencies.Libraries.CAMERA_X_VIEW)

    // ML Kit
    implementation(Dependencies.Libraries.ML_KIT_TEXT_RECOGNITION)

    // Tests
    addCoreTestLibraries()
}