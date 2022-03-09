import dependency.Dependencies
import extension.addCoreTestLibraries
import extension.addDagger

plugins {
    `base-lib`
}

dependencies {
    implementation(projects.core)

    // ML Kit
    implementation(Dependencies.Libraries.ML_KIT_TEXT_RECOGNITION)

    // Hilt
    addDagger()

    // Tests
    addCoreTestLibraries()
}
