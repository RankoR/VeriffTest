package com.example.sdk.di.module

import android.content.Context
import com.example.core.di.APPLICATION_CONTEXT
import com.example.core.di.DISPATCHER_DEFAULT
import com.example.sdk.domain.face.interactor.DetectFace
import com.example.sdk.domain.face.interactor.DetectFaceImpl
import dagger.Module
import dagger.Provides
import dagger.Reusable
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Named

@Module
class FaceDetectionModule {

    @Provides
    @Reusable
    fun provideDetectFace(
        @Named(APPLICATION_CONTEXT)
        context: Context,
        @Named(DISPATCHER_DEFAULT)
        dispatcher: CoroutineDispatcher
    ): DetectFace = DetectFaceImpl(context, dispatcher)
}
