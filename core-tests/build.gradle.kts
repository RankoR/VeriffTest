import dependency.Dependencies

plugins {
    `base-lib`
}

dependencies {
    implementation(projects.core)

    implementation(Dependencies.Libraries.JUNIT)
    implementation(Dependencies.Libraries.KOTLIN_TEST)
    implementation(Dependencies.Libraries.ANDROIDX_TEST_CORE)
    implementation(Dependencies.Libraries.COROUTINES_TEST)
    implementation(Dependencies.Libraries.ESPRESSO_CORE)
}
