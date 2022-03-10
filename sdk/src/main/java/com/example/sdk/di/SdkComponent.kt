package com.example.sdk.di

import com.example.core.di.CoreModule
import com.example.core_ui.di.viewmodel.ViewModelModule
import com.example.sdk.presentation.IdRecognitionActivity
import com.example.text_detection.di.TextDetectionModule
import dagger.Component
import timber.log.Timber
import javax.inject.Singleton

@Component(
    modules = [
        CoreModule::class,
        ViewModelModule::class,
        SdkViewModelsModule::class,
        TextDetectionModule::class
    ]
)
@Singleton
interface SdkComponent {

    val loggingTree: Timber.Tree

    fun inject(activity: IdRecognitionActivity)
}
