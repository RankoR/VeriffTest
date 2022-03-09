import extension.addCoreTestLibraries
import extension.addHilt

plugins {
    `base-lib`
}

dependencies {
    implementation(projects.core)
    implementation(projects.coreUi)

    // Hilt
    addHilt()

    // Tests
    addCoreTestLibraries()
}
