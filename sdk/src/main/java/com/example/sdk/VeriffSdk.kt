package com.example.sdk

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.FragmentActivity
import com.example.sdk.data.model.id.TextDocumentResult
import com.example.sdk.di.DiHolder
import com.example.sdk.presentation.id.IdRecognitionActivity
import com.example.sdk.presentation.id.IdRecognitionContract
import timber.log.Timber
import java.lang.ref.WeakReference

object VeriffSdk {

    private var activity: WeakReference<FragmentActivity>? = null

    private var idRecognitionLauncher: ActivityResultLauncher<Unit>? = null

    var isInitialized = false
        private set

    var onTextDocumentResult: ((result: TextDocumentResult) -> Unit) = {}

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

            onTextDocumentResult.invoke(textDocumentResult)
        }

        // TODO: Face activity
    }

    fun launchIdRecognition() {
        idRecognitionLauncher?.launch(Unit)
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
}
