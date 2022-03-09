import extension.addCoreTestLibraries
import extension.addHilt

plugins {
    `base-lib`
}

dependencies {
    // Hilt
    addHilt()

    // Tests
    addCoreTestLibraries()
}
