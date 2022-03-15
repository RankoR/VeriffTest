package com.example.sdk.di.component

import com.example.core.di.CoreModule
import com.example.core_ui.di.viewmodel.ViewModelModule
import com.example.sdk.di.module.FaceDetectionModule
import com.example.sdk.di.module.SdkViewModelsModule
import com.example.sdk.di.module.TextExtractionModule
import com.example.sdk.presentation.face.FaceRecognitionActivity
import com.example.sdk.presentation.id.IdRecognitionActivity
import dagger.Component
import timber.log.Timber
import javax.inject.Singleton

/**
 * Main dependencies (singleton and reusable)
 */
@Component(
    modules = [
        CoreModule::class,
        ViewModelModule::class,
        SdkViewModelsModule::class,
        TextExtractionModule::class,
        FaceDetectionModule::class
    ]
)
@Singleton
interface SdkComponent {

    val fragmentComponentBuilder: FragmentComponent.Builder

    /**
     * We can't inject into object (it's used in VeriffSdk object), so providing it as a variable
     */
    val loggingTree: Timber.Tree

    fun inject(activity: IdRecognitionActivity)
    fun inject(activity: FaceRecognitionActivity)
}
