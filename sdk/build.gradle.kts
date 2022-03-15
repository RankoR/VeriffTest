import dependency.Dependencies
import extension.addCoreTestLibraries
import extension.addDagger
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `base-lib`
    id("kotlin-parcelize")
}

android {
    buildTypes {
        debug {
            isTestCoverageEnabled = true
        }
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"
    }
}

dependencies {
    implementation(projects.core)
    implementation(projects.coreUi)

    // ML Kit
    implementation(Dependencies.Libraries.ML_KIT_TEXT_RECOGNITION)
    implementation(Dependencies.Libraries.ML_KIT_FACE_DETECTION)

    // CameraX
    implementation(Dependencies.Libraries.CAMERA_X_CORE)
    implementation(Dependencies.Libraries.CAMERA_X)
    implementation(Dependencies.Libraries.CAMERA_X_LIFECYCLE)
    implementation(Dependencies.Libraries.CAMERA_X_VIEW)

    // Dagger
    addDagger()

    debugImplementation(Dependencies.Libraries.LEAK_CANARY)

    // Tests
    addCoreTestLibraries()
}

// tasks.withType<Test> {
//    configure<JacocoTaskExtension> {
//        isIncludeNoLocationClasses = true
//    }
// }

configurations.all {
    resolutionStrategy {
        eachDependency {
            if ("org.jacoco" == requested.group) {
                useVersion("0.8.7")
            }
        }
    }
}

task<JacocoReport>("mergedJacocoReport") {
    dependsOn("testDebugUnitTest", "createDebugCoverageReport")

    reports {
        xml.required.set(true)
        html.required.set(true)
    }

    val fileFilter = mutableSetOf(
        "**/R.class",
        "**/R\$*.class",
        "**/BuildConfig.*",
        "**/Manifest*.*",
        "**/*Test*.*",
        "android/**/*.*",
        "**/*\$Lambda$*.*", // Jacoco can not handle several "$" in class name.
        "**/*\$inlined$*.*", // Kotlin specific, Jacoco can not handle several "$" in class name.
        "**/com/example/sdk/di/DiHolder.class" // Not working for some reason
    )

    classDirectories.setFrom(
        fileTree(project.buildDir) {
            include(
                "**/classes/**/main/**",
                "**/intermediates/classes/debug/**",
                "**/intermediates/javac/debug/*/classes/**", // Android Gradle Plugin 3.2.x support.
                "**/tmp/kotlin-classes/debug/**"
            )

            exclude(fileFilter)
        }
    )

    sourceDirectories.setFrom(
        fileTree("${project.buildDir}") {
            include(
                "src/main/java/**",
                "src/main/kotlin/**",
                "src/debug/java/**",
                "src/debug/kotlin/**"
            )
        }
    )

    executionData.setFrom(
        fileTree(project.buildDir) {
            include(
                "outputs/code_coverage/**/*.ec",
                "jacoco/jacocoTestReportDebug.exec",
                "jacoco/testDebugUnitTest.exec",
                "jacoco/test.exec"
            )
        }
    )
}
