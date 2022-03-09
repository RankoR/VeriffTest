@file:Suppress("UnstableApiUsage")

include(":sdk")


enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

include(
    ":app",
    ":sdk"
)

rootProject.name = "VeriffTest"
