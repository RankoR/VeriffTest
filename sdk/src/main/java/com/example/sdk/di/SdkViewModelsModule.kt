package com.example.sdk.di

import androidx.lifecycle.ViewModel
import com.example.core_ui.di.viewmodel.ViewModelKey
import com.example.sdk.presentation.IdRecognitionViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class SdkViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(IdRecognitionViewModel::class)
    abstract fun bindIdRecognitionViewModel(
        viewModel: IdRecognitionViewModel
    ): ViewModel
}
