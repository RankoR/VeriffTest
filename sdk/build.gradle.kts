import extension.addCoreTestLibraries
import extension.addHilt

plugins {
    `base-lib`
    id("com.google.dagger.hilt.android")
}

dependencies {
    implementation(projects.core)
    implementation(projects.coreUi)
    implementation(projects.textDetection)

    // Hilt
    addHilt()

    // Tests
    addCoreTestLibraries()
}
