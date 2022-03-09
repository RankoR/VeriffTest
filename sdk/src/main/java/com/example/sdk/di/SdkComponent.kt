package com.example.sdk.di

import com.example.core.di.CoreModule
import com.example.text_detection.di.TextDetectionModule
import dagger.Component
import timber.log.Timber
import javax.inject.Singleton

@Component(
    modules = [
        CoreModule::class,
        TextDetectionModule::class
    ]
)
@Singleton
interface SdkComponent {

    val loggingTree: Timber.Tree
}
