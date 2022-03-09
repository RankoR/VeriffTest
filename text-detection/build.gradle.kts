import dependency.Dependencies
import extension.addCoreTestLibraries
import extension.addHilt

plugins {
    `base-lib`
    id("com.google.dagger.hilt.android")
}

dependencies {
    implementation(projects.core)

    // ML Kit
    implementation(Dependencies.Libraries.ML_KIT_TEXT_RECOGNITION)

    // Hilt
    addHilt()

    // Tests
    addCoreTestLibraries()
}
