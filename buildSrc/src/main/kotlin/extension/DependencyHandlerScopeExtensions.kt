package extension

import dependency.Dependencies
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.ProjectDependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.project

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

private fun DependencyHandler.testImplementation(projectDependency: ProjectDependency): Dependency? {
    return add("testImplementation", projectDependency)
}

private fun DependencyHandler.androidTestImplementation(projectDependency: ProjectDependency): Dependency? {
    return add("androidTestImplementation", projectDependency)
}

fun DependencyHandlerScope.addDagger() {
    implementation(Dependencies.Libraries.DAGGER)
    kapt(Dependencies.Libraries.DAGGER_COMPILER)
}

fun DependencyHandlerScope.addCoreTestLibraries() {
    testImplementation(Dependencies.Libraries.JUNIT)
    androidTestImplementation(Dependencies.Libraries.ANDROIDX_TEST_CORE)
    androidTestImplementation(Dependencies.Libraries.ANDROIDX_TEST_RUNNER)
    androidTestImplementation(Dependencies.Libraries.ANDROIDX_TEST_RULES)
    androidTestImplementation(Dependencies.Libraries.ANDROIDX_TEST_EXT_JUNIT)
    androidTestImplementation(Dependencies.Libraries.ANDROIDX_FRAGMENT_TESTING)
    androidTestImplementation(Dependencies.Libraries.ESPRESSO_CORE)

    testImplementation(Dependencies.Libraries.COROUTINES_TEST)
    androidTestImplementation(Dependencies.Libraries.COROUTINES_TEST)

    testImplementation(Dependencies.Libraries.KOTLIN_TEST)
    androidTestImplementation(Dependencies.Libraries.KOTLIN_TEST)

    testImplementation(project(Dependencies.Libraries.CORE_TESTS))
    androidTestImplementation(project(Dependencies.Libraries.CORE_TESTS))
}