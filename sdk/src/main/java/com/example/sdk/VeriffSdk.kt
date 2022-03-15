package com.example.sdk

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.FragmentActivity
import com.example.sdk.VeriffSdk.launchFaceRecognition
import com.example.sdk.VeriffSdk.launchIdRecognition
import com.example.sdk.VeriffSdk.registerActivity
import com.example.sdk.data.model.face.FaceResult
import com.example.sdk.data.model.id.TextDocumentResult
import com.example.sdk.di.DiHolder
import com.example.sdk.presentation.face.FaceRecognitionActivity
import com.example.sdk.presentation.face.FaceRecognitionContract
import com.example.sdk.presentation.id.IdRecognitionActivity
import com.example.sdk.presentation.id.IdRecognitionContract
import timber.log.Timber

/**
 * Main Veriff SDK object
 *
 * Call [registerActivity] on [Activity] creation, then
 * call [launchIdRecognition] to launch the ID recognition or [launchFaceRecognition] for face recognition
 */
object VeriffSdk {

    private var idRecognitionLauncher: ActivityResultLauncher<Unit>? = null
    private var faceRecognitionLauncher: ActivityResultLauncher<Unit>? = null

    var isInitialized = false
        private set

    /**
     * Invoked on ID recognition result
     *
     * @see TextDocumentResult
     */
    var onTextDocumentResult: ((result: TextDocumentResult) -> Unit) = {}

    /**
     * Invoked on face detection result
     *
     * @see FaceResult
     */
    var onFaceResult: ((result: FaceResult) -> Unit) = {}

    /**
     * Called once to initialize the required components, such as DI and logging
     *
     * @param application [Application] instance for initialization
     */
    @Synchronized
    private fun initialize(application: Application) {
        initializeDi(application)
        initializeLogging()

        isInitialized = true
    }

    /**
     * Call this method in every activity, where you plan to launch the verification flows.
     *
     * If you'll not call it, flows won't be launched
     *
     * @param activity [FragmentActivity] to register
     */
    fun registerActivity(activity: FragmentActivity) {
        if (!isInitialized) {
            initialize(activity.application)
        }

        idRecognitionLauncher = activity.registerForActivityResult(IdRecognitionContract()) { textDocumentResult ->
            Timber.d("Document result: $textDocumentResult")

            onTextDocumentResult(textDocumentResult)
        }

        faceRecognitionLauncher = activity.registerForActivityResult(FaceRecognitionContract()) { faceResult ->
            Timber.d("Face result: $faceResult")

            onFaceResult(faceResult)
        }
    }

    /**
     * Launch the ID recognition flow.
     *
     * After the flow is complete (or cancelled) you'll get [onTextDocumentResult] callback invoked
     *
     * @see onTextDocumentResult
     */
    fun launchIdRecognition() {
        idRecognitionLauncher?.launch(Unit)
    }

    /**
     * Launch the face recognition flow.
     *
     * After the flow is complete (or cancelled) you'll get [onFaceResult] callback invoked
     *
     * @see onFaceResult
     */
    fun launchFaceRecognition() {
        faceRecognitionLauncher?.launch(Unit)
    }

    /**
     * Initialize the dagger DI component
     *
     * @param application [Application] instance to use for initialization
     */
    private fun initializeDi(application: Application) {
        DiHolder.initialize(application)
    }

    /**
     * Initialize logging
     */
    private fun initializeLogging() {
        Timber.plant(DiHolder.sdkComponent.loggingTree)

        Timber.d("Initialized logging")
    }

    /**
     * Create [Intent] for launching the ID recognition activity
     *
     * @param context [Context] to use (must be an activity context, otherwise throws [IllegalArgumentException])
     * @return [Intent] for launching the [IdRecognitionActivity]
     */
    internal fun createIdRecognitionIntent(context: Context): Intent {
        require(context is Activity) { "You must pass an Activity context" }

        return Intent(context, IdRecognitionActivity::class.java)
    }

    /**
     * Create [Intent] for launching the face recognition activity
     *
     * @param context [Context] to use (must be an activity context, otherwise throws [IllegalArgumentException])
     * @return [Intent] for launching the [FaceRecognitionActivity]
     */
    internal fun createFaceRecognitionIntent(context: Context): Intent {
        require(context is Activity) { "You must pass an Activity context" }

        return Intent(context, FaceRecognitionActivity::class.java)
    }
}
