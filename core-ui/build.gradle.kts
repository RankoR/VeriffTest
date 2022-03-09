
import dependency.Dependencies
import extension.addCoreTestLibraries

plugins {
    `base-lib`
}

dependencies {
    implementation(projects.core)

    // AndroidX
    api(Dependencies.Libraries.ANDROID_X_APPCOMPAT)
    api(Dependencies.Libraries.ANDROID_X_CONSTRAINT_LAYOUT)
    api(Dependencies.Libraries.ANDROID_X_FRAGMENT_KTX)
    api(Dependencies.Libraries.ANDROID_X_LIFECYCLE_RUNTIME_KTX)
    api(Dependencies.Libraries.ANDROID_X_LIFECYCLE_VIEW_MODEL_KTX)
    api(Dependencies.Libraries.ANDROID_X_LIFECYCLE_VIEW_MODEL_SAVED_STATE)
    api(Dependencies.Libraries.ANDROID_X_LIFECYCLE_COMMON_JAVA8)
    api(Dependencies.Libraries.ANDROID_X_LIFECYCLE_EXTENSIONS)

    // Material
    api(Dependencies.Libraries.GOOGLE_MATERIAL)

    // Tests
    addCoreTestLibraries()
}
