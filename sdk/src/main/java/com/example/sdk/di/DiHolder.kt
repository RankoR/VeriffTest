package com.example.sdk.di

import android.app.Application
import com.example.core.di.CoreModule
import com.example.sdk.di.DiHolder.initialize
import com.example.sdk.di.component.DaggerSdkComponent
import com.example.sdk.di.component.SdkComponent
import com.example.sdk.di.module.TextExtractionModule
import timber.log.Timber

/**
 * Holds the DI components ([SdkComponent] for now)
 *
 * Call [initialize] once to create DI components
 *
 * @see SdkComponent
 */
internal object DiHolder {

    /**
     * Main SDK component (singleton)
     *
     * @see SdkComponent
     */
    lateinit var sdkComponent: SdkComponent
        private set

    /**
     * Indicates if [sdkComponent] was initialized
     */
    private val isInitialized: Boolean
        get() = ::sdkComponent.isInitialized

    /**
     * Initialize the DI holder, creating the [SdkComponent]
     *
     * Safe to call more than once — in this case initialization would be skipped
     *
     * @param application [Application] to use for initialization
     */
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
