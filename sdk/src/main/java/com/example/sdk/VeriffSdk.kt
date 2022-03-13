package com.example.sdk

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.FragmentActivity
import com.example.sdk.data.model.face.FaceResult
import com.example.sdk.data.model.id.TextDocumentResult
import com.example.sdk.di.DiHolder
import com.example.sdk.presentation.face.FaceRecognitionActivity
import com.example.sdk.presentation.face.FaceRecognitionContract
import com.example.sdk.presentation.id.IdRecognitionActivity
import com.example.sdk.presentation.id.IdRecognitionContract
import timber.log.Timber
import java.lang.ref.WeakReference

object VeriffSdk {

    private var activity: WeakReference<FragmentActivity>? = null

    private var idRecognitionLauncher: ActivityResultLauncher<Unit>? = null
    private var faceRecognitionLauncher: ActivityResultLauncher<Unit>? = null

    var isInitialized = false
        private set

    var onTextDocumentResult: ((result: TextDocumentResult) -> Unit) = {}
    var onFaceResult: ((result: FaceResult) -> Unit) = {}

    fun initialize(application: Application) {
        if (isInitialized) {
            throw IllegalStateException("SDK is already initialized")
        }

        initializeDi(application)
        initializeLogging()

        isInitialized = true
    }

    fun registerActivity(activity: FragmentActivity) {
        this.activity = WeakReference(activity)

        idRecognitionLauncher = activity.registerForActivityResult(IdRecognitionContract()) { textDocumentResult ->
            Timber.d("Document result: $textDocumentResult")

            onTextDocumentResult(textDocumentResult)
        }

        faceRecognitionLauncher = activity.registerForActivityResult(FaceRecognitionContract()) { faceResult ->
            Timber.d("Face result: $faceResult")

            onFaceResult(faceResult)
        }
    }

    fun launchIdRecognition() {
        idRecognitionLauncher?.launch(Unit)
    }

    fun launchFaceRecognition() {
        faceRecognitionLauncher?.launch(Unit)
    }

    private fun initializeDi(application: Application) {
        DiHolder.initialize(application)
    }

    private fun initializeLogging() {
        Timber.plant(DiHolder.sdkComponent.loggingTree)

        Timber.d("Initialized logging")
    }

    internal fun createIdRecognitionIntent(context: Context): Intent {
        require(context is Activity) { "You must pass an Activity context" } // TODO: Document it

        return Intent(context, IdRecognitionActivity::class.java)
    }

    internal fun createFaceRecognitionIntent(context: Context): Intent {
        require(context is Activity) { "You must pass an Activity context" } // TODO: Document it

        return Intent(context, FaceRecognitionActivity::class.java)
    }
}
