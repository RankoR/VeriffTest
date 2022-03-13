import dependency.Dependencies
import extension.addCoreTestLibraries
import extension.addDagger
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `base-lib`
    id("kotlin-parcelize")
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

dependencies {
    implementation(projects.core)
    implementation(projects.coreUi)

    // ML Kit
    implementation(Dependencies.Libraries.ML_KIT_TEXT_RECOGNITION)
    implementation(Dependencies.Libraries.ML_KIT_FACE_DETECTION)

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
