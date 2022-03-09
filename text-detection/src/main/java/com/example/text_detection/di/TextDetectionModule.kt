package com.example.text_detection.di

import com.example.core.di.DISPATCHER_DEFAULT
import com.example.text_detection.domain.ExtractText
import com.example.text_detection.domain.ExtractTextImpl
import dagger.Module
import dagger.Provides
import dagger.Reusable
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Named

@Module
class TextDetectionModule {

    @Provides
    @Reusable
    fun provideExtractText(
        @Named(DISPATCHER_DEFAULT)
        dispatcher: CoroutineDispatcher
    ): ExtractText = ExtractTextImpl(dispatcher)
}
