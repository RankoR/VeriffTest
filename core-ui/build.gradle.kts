
import extension.addCoreTestLibraries
import extension.addHilt

plugins {
    `base-lib`
}

dependencies {
    implementation(projects.core)

    // Hilt
    addHilt()

    // Tests
    addCoreTestLibraries()
}
