package extension

import dependency.Dependencies
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.DependencyHandlerScope

private fun DependencyHandler.implementation(dependencyNotation: String): Dependency? {
    return add("implementation", dependencyNotation)
}

private fun DependencyHandler.kapt(dependencyNotation: String): Dependency? {
    return add("kapt", dependencyNotation)
}

private fun DependencyHandler.testImplementation(dependencyNotation: String): Dependency? {
    return add("testImplementation", dependencyNotation)
}

private fun DependencyHandler.androidTestImplementation(dependencyNotation: String): Dependency? {
    return add("androidTestImplementation", dependencyNotation)
}

fun DependencyHandlerScope.addHilt() {
    implementation(Dependencies.Libraries.HILT_ANDROID)
    kapt(Dependencies.Libraries.HILT_COMPILER)
    kapt(Dependencies.Libraries.HILT_ANDROID_COMPILER)
}

fun DependencyHandlerScope.addCoreTestLibraries() {
    testImplementation(Dependencies.Libraries.JUNIT)
    androidTestImplementation(Dependencies.Libraries.ANDROIDX_TEST_CORE)
    androidTestImplementation(Dependencies.Libraries.ANDROIDX_TEST_RUNNER)
    androidTestImplementation(Dependencies.Libraries.ANDROIDX_TEST_RULES)
    androidTestImplementation(Dependencies.Libraries.ANDROIDX_TEST_EXT_JUNIT)

    testImplementation(Dependencies.Libraries.COROUTINES_TEST)
    androidTestImplementation(Dependencies.Libraries.COROUTINES_TEST)
}