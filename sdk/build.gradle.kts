import extension.addCoreTestLibraries
import extension.addDagger

plugins {
    `base-lib`
}

dependencies {
    implementation(projects.core)
    implementation(projects.coreUi)
    implementation(projects.textDetection)

    // Hilt
    addDagger()

    // Tests
    addCoreTestLibraries()
}
