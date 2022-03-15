package com.example.sdk.di.module

import android.content.Context
import com.example.core.di.APPLICATION_CONTEXT
import com.example.core.di.DISPATCHER_DEFAULT
import com.example.sdk.domain.id.interactor.ExtractText
import com.example.sdk.domain.id.interactor.ExtractTextImpl
import dagger.Module
import dagger.Provides
import dagger.Reusable
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Named

/**
 * Dependencies related to text extraction
 */
@Module
internal class TextExtractionModule {

    @Provides
    @Reusable
    fun provideExtractText(
        @Named(APPLICATION_CONTEXT)
        context: Context,
        @Named(DISPATCHER_DEFAULT)
        dispatcher: CoroutineDispatcher
    ): ExtractText = ExtractTextImpl(context, dispatcher)
}
