package com.example.sdk.di

import android.app.Application
import com.example.core.di.CoreModule
import timber.log.Timber

internal object DiHolder {

    lateinit var sdkComponent: SdkComponent

    private val isInitialized: Boolean
        get() = ::sdkComponent.isInitialized

    fun initialize(application: Application) {
        if (isInitialized) {
            Timber.w("SDK is already initialized")
            return
        }

        sdkComponent = DaggerSdkComponent
            .builder()
            .coreModule(CoreModule(application))
            .textDetectionModule(TextDetectionModule())
            .build()
    }
}
