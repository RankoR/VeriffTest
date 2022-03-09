import dependency.Dependencies
import extension.addCoreTestLibraries
import extension.addDagger

plugins {
    `base-lib`
}

dependencies {
    // Kotlin
    api(Dependencies.Libraries.KOTLIN_STD_LIB)
    api(Dependencies.Libraries.ANDROID_X_CORE_KTX)

    // Coroutines
    api(Dependencies.Libraries.COROUTINES_CORE)
    api(Dependencies.Libraries.COROUTINES_ANDROID)

    // Timber
    api(Dependencies.Libraries.TIMBER)

    // Hilt
    addDagger()

    // Tests
    addCoreTestLibraries()
}
