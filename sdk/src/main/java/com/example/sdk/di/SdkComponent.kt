package com.example.sdk.di

import com.example.core.di.CoreModule
import com.example.core_ui.di.viewmodel.ViewModelModule
import com.example.sdk.presentation.camera.CameraFragment
import com.example.sdk.presentation.id.IdRecognitionActivity
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

    fun inject(fragment: CameraFragment)
}
