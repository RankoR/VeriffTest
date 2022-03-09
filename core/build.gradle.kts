import dependency.Dependencies
import extension.addCoreTestLibraries
import extension.addHilt

plugins {
    `base-lib`
}

dependencies {
    // Kotlin
    api(Dependencies.Libraries.KOTLIN_STD_LIB)
    api(Dependencies.Libraries.ANDROID_X_CORE_KTX)

    // Coroutines
    implementation(Dependencies.Libraries.COROUTINES_CORE)
    implementation(Dependencies.Libraries.COROUTINES_ANDROID)

    // Hilt
    addHilt()

    // Tests
    addCoreTestLibraries()
}
