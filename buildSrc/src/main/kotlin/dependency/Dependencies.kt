package dependency

object Dependencies {

    // Libraries
    object Libraries {

        // Kotlin
        const val KOTLIN_STD_LIB = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.KOTLIN}"

        // Coroutines
        const val COROUTINES_CORE = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.COROUTINES}"
        const val COROUTINES_ANDROID = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.COROUTINES}"

        // Hilt
        const val HILT_ANDROID = "com.google.dagger:hilt-android:${Versions.HILT}"
        const val HILT_COMPILER = "com.google.dagger:hilt-compiler:${Versions.HILT}"
        const val HILT_ANDROID_COMPILER = "com.google.dagger:hilt-android-compiler:${Versions.HILT}"
        const val ANDROID_X_HILT_COMPILER = "androidx.hilt:hilt-compiler:${Versions.ANDROID_X_HILT}"

        // AndroidX
        const val ANDROID_X_CORE_KTX = "androidx.core:core-ktx:${Versions.ANDROID_X_CORE_KTX}"
        const val ANDROID_X_COLLECTION_KTX = "androidx.collection:collection-ktx:${Versions.ANDROID_X_COLLECTION_KTX}"
        const val ANDROID_X_APPCOMPAT = "androidx.appcompat:appcompat:${Versions.ANDROID_X_APPCOMPAT}"
        const val ANDROID_X_CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout:${Versions.ANDROID_X_CONSTRAINT_LAYOUT}"
        const val ANDROID_X_LIFECYCLE_RUNTIME_KTX = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.ANDROID_X_LIFECYCLE}"

        const val ANDROID_X_LIFECYCLE_VIEW_MODEL_KTX = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.ANDROID_X_LIFECYCLE}"
        const val ANDROID_X_LIFECYCLE_VIEW_MODEL_SAVED_STATE = "androidx.lifecycle:lifecycle-viewmodel-savedstate:${Versions.ANDROID_X_LIFECYCLE}"
        const val ANDROID_X_LIFECYCLE_SERVICE = "androidx.lifecycle:lifecycle-service:${Versions.ANDROID_X_LIFECYCLE}"
        const val ANDROID_X_LIFECYCLE_PROCESS = "androidx.lifecycle:lifecycle-process:${Versions.ANDROID_X_LIFECYCLE}"
        const val ANDROID_X_LIFECYCLE_COMMON_JAVA8 = "androidx.lifecycle:lifecycle-common-java8:${Versions.ANDROID_X_LIFECYCLE}"
        const val ANDROID_X_LIFECYCLE_EXTENSIONS = "androidx.lifecycle:lifecycle-extensions:${Versions.ANDROID_X_LIFECYCLE_EXTENSIONS}"

        // Google material
        const val GOOGLE_MATERIAL = "com.google.android.material:material:${Versions.GOOGLE_MATERIAL}"

        // Timber
        const val TIMBER = "com.jakewharton.timber:timber:${Versions.TIMBER}"

        // CameraX
        const val CAMERA_X = "androidx.camera:camera-camera2:${Versions.CAMERA_X}"
        const val CAMERA_X_LIFECYCLE = "androidx.camera:camera-lifecycle:${Versions.CAMERA_X}"
        const val CAMERA_X_VIEW ="androidx.camera:camera-view:${Versions.CAMERA_X_VIEW}"

        // ML Kit
        const val ML_KIT_TEXT_RECOGNITION = "com.google.mlkit:text-recognition:${Versions.ML_KIT}"

        // Test
        const val JUNIT = "junit:junit:${Versions.JUNIT}"
        const val COROUTINES_TEST = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.COROUTINES}"
        const val ANDROIDX_TEST_CORE = "androidx.test:core:${Versions.ANDROIDX_TEST}"
        const val ANDROIDX_TEST_RUNNER = "androidx.test:runner:${Versions.ANDROIDX_TEST}"
        const val ANDROIDX_TEST_RULES = "androidx.test:rules:${Versions.ANDROIDX_TEST}"
        const val ANDROIDX_TEST_EXT_JUNIT = "androidx.test.ext:junit:${Versions.ANDROIDX_TEST_EXT_JUNIT}"

        /**
         * Versions
         */
        private object Versions {
            const val KOTLIN = "1.6.10"

            const val COROUTINES = "1.6.0"

            const val HILT = "2.41"
            const val ANDROID_X_HILT = "1.0.0"

            // AndroidX
            const val ANDROID_X_CORE_KTX = "1.7.0"
            const val ANDROID_X_COLLECTION_KTX = "1.2.0"
            const val ANDROID_X_APPCOMPAT = "1.4.1"
            const val ANDROID_X_CONSTRAINT_LAYOUT = "2.1.2"
            const val ANDROID_X_FRAGMENT_KTX = "1.4.1"
            const val ANDROID_X_LIFECYCLE = "2.4.1"
            const val ANDROID_X_LIFECYCLE_EXTENSIONS = "2.2.0"

            const val GOOGLE_MATERIAL = "1.5.0"

            const val TIMBER = "5.0.1"

            // CameraX
            const val CAMERA_X = "1.0.2"
            const val CAMERA_X_VIEW = "1.1.0-beta02"

            // ML Kit
            const val ML_KIT = "16.0.0-beta3"

            // Tests
            const val JUNIT = "4.13.2"
            const val ANDROIDX_TEST = "1.4.0"
            const val ANDROIDX_TEST_EXT_JUNIT = "1.1.3"
        }
    }
}