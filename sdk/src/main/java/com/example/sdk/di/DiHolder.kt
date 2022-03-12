package com.example.sdk.di

import android.app.Application
import com.example.core.di.CoreModule
import com.example.sdk.di.component.DaggerSdkComponent
import com.example.sdk.di.component.SdkComponent
import com.example.sdk.di.module.TextExtractionModule
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
            .textExtractionModule(TextExtractionModule())
            .build()
    }
}
