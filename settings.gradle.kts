@file:Suppress("UnstableApiUsage")

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

include(
    ":core",
    ":core-tests",
    ":core-ui",
    ":text-detection",
    ":sdk",

    ":app"
)

rootProject.name = "VeriffTest"
