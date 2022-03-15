package com.example.sdk.di.module

import androidx.lifecycle.ViewModel
import com.example.core_ui.di.viewmodel.ViewModelKey
import com.example.sdk.presentation.face.FaceRecognitionViewModel
import com.example.sdk.presentation.id.IdRecognitionViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * ViewModels injection.
 *
 * We're using this technique to be able to inject ViewModels constructors.
 * As if we'll use properties injection, we'll lose the ability to mock this properties.
 */
@Module
abstract class SdkViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(IdRecognitionViewModel::class)
    abstract fun bindIdRecognitionViewModel(
        viewModel: IdRecognitionViewModel
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FaceRecognitionViewModel::class)
    abstract fun bindFaceRecognitionViewModel(
        viewModel: FaceRecognitionViewModel
    ): ViewModel
}
