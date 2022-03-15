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
    implementation(projects.coreUi)
    implementation(projects.sdk)

    // Needed to get an image rotation
    implementation(Dependencies.Libraries.EXIF_INTERFACE)

    // JaCoCo agent
    implementation(Dependencies.Libraries.JACOCO_AGENT)

    // Tests
    addCoreTestLibraries()
}
