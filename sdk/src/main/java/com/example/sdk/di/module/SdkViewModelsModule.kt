package com.example.sdk.di.module

import androidx.lifecycle.ViewModel
import com.example.core_ui.di.viewmodel.ViewModelKey
import com.example.sdk.presentation.id.IdRecognitionViewModel
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
