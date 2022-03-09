
import extension.addCoreTestLibraries

plugins {
    `base-lib`
}

dependencies {
    implementation(projects.core)

    // AndroidX
    api(dependency.Dependencies.Libraries.ANDROID_X_APPCOMPAT)
    api(dependency.Dependencies.Libraries.ANDROID_X_CONSTRAINT_LAYOUT)

    // Material
    api(dependency.Dependencies.Libraries.GOOGLE_MATERIAL)

    // Tests
    addCoreTestLibraries()
}
