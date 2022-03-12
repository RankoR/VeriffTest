import dependency.Dependencies
import extension.addCoreTestLibraries
import extension.addDagger
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `base-lib`
    id("kotlin-parcelize")
    id("com.dicedmelon.gradle.jacoco-android") version "0.1.5"
}

android {
    buildTypes {
        debug {
            isTestCoverageEnabled = true
        }
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"
    }
}

jacocoAndroidUnitTestReport {
    csv.enabled(false)
    html.enabled(true)
    xml.enabled(true)

    excludes = excludes + setOf(
        "com/example/sdk/databinding/*.class"
    )
}

// Fix for JaCoCo: https://youtrack.jetbrains.com/issue/KT-44757
configurations.all {
    resolutionStrategy {
        eachDependency {
            if (requested.group == "org.jacoco") {
                useVersion("0.8.7")
            }
        }
    }
}

dependencies {
    implementation(projects.core)
    implementation(projects.coreUi)

    // ML Kit
    implementation(Dependencies.Libraries.ML_KIT_TEXT_RECOGNITION)

    // CameraX
    implementation(Dependencies.Libraries.CAMERA_X_CORE)
    implementation(Dependencies.Libraries.CAMERA_X)
    implementation(Dependencies.Libraries.CAMERA_X_LIFECYCLE)
    implementation(Dependencies.Libraries.CAMERA_X_VIEW)

    // Dagger
    addDagger()

    debugImplementation(Dependencies.Libraries.LEAK_CANARY)

    // Tests
    addCoreTestLibraries()
}
