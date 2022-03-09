import extension.addCoreTestLibraries
import extension.addHilt

plugins {
    `base-lib`
    id("com.google.dagger.hilt.android")
}

dependencies {
    implementation(projects.core)
    implementation(projects.coreUi)

    // Hilt
    addHilt()

    // Tests
    addCoreTestLibraries()
}
