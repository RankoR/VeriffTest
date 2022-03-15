package com.example.sdk.di.module

import android.content.Context
import com.example.core.di.ACTIVITY_CONTEXT
import com.example.core_ui.di.scope.FragmentScope
import com.example.sdk.domain.camera.CameraProviderWrapper
import com.example.sdk.domain.camera.CameraProviderWrapperImpl
import dagger.Module
import dagger.Provides
import javax.inject.Named

/**
 * Dependencies related to fragment scope
 */
@Module
internal class FragmentModule(
    private val context: Context
) {

    @Provides
    @FragmentScope
    @Named(ACTIVITY_CONTEXT)
    fun provideActivityContext(): Context = context

    @Provides
    @FragmentScope
    fun provideCameraProviderWrapper(
        @Named(ACTIVITY_CONTEXT)
        context: Context
    ): CameraProviderWrapper = CameraProviderWrapperImpl(context)
}
