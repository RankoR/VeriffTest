package com.example.sdk

import android.app.Activity
import android.app.Application
import android.content.Intent
import com.example.core.di.CoreModule
import com.example.sdk.di.DaggerSdkComponent
import com.example.sdk.di.SdkComponent
import com.example.sdk.presentation.IdRecognitionActivity
import com.example.text_detection.di.TextDetectionModule
import timber.log.Timber

object VeriffSdk {

    internal lateinit var sdkComponent: SdkComponent

    fun initialize(application: Application) {
        if (::sdkComponent.isInitialized) {
            throw IllegalStateException("SDK is already initialized")
        }

        initializeDi(application)
        initializeLogging()
    }

    private fun initializeDi(application: Application) {
        sdkComponent = DaggerSdkComponent
            .builder()
            .coreModule(CoreModule(application))
            .textDetectionModule(TextDetectionModule())
            .build()
    }

    private fun initializeLogging() {
        Timber.plant(sdkComponent.loggingTree)

        Timber.d("Initialized logging")
    }

    fun createIdRecognitionIntent(activity: Activity): Intent {
        return Intent(activity, IdRecognitionActivity::class.java)
    }
}
