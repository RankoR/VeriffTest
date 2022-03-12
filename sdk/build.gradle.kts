import dependency.Dependencies
import extension.addCoreTestLibraries
import extension.addDagger

plugins {
    `base-lib`
    id("kotlin-parcelize")
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
