package com.example.sdk

import android.app.Activity
import android.app.Application
import android.content.Intent
import com.example.sdk.di.DiHolder
import com.example.sdk.presentation.id.IdRecognitionActivity
import timber.log.Timber

object VeriffSdk {

    var isInitialized = false
        private set

    fun initialize(application: Application) {
        if (isInitialized) {
            throw IllegalStateException("SDK is already initialized")
        }

        initializeDi(application)
        initializeLogging()

        isInitialized = true
    }

    private fun initializeDi(application: Application) {
        DiHolder.initialize(application)
    }

    private fun initializeLogging() {
        Timber.plant(DiHolder.sdkComponent.loggingTree)

        Timber.d("Initialized logging")
    }

    fun createIdRecognitionIntent(activity: Activity): Intent {
        return Intent(activity, IdRecognitionActivity::class.java)
    }
}
