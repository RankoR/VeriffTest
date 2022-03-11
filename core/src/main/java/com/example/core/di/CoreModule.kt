package com.example.core.di

import android.app.Application
import android.content.Context
import com.example.core.domain.ArePermissionsGranted
import com.example.core.domain.ArePermissionsGrantedImpl
import com.example.core.domain.IsDebug
import com.example.core.domain.IsDebugImpl
import dagger.Module
import dagger.Provides
import dagger.Reusable
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import timber.log.Timber
import javax.inject.Named
import javax.inject.Singleton

@Module
class CoreModule(
    private val application: Application
) {

    @Provides
    @Singleton
    @Named(APPLICATION_CONTEXT)
    fun provideApplicationContext(): Context = application

    @Provides
    @Reusable
    fun provideIsDebug(): IsDebug = IsDebugImpl()

    @Provides
    @Reusable
    fun provideArePermissionsGranted(
        @Named(APPLICATION_CONTEXT)
        context: Context
    ): ArePermissionsGranted = ArePermissionsGrantedImpl(context)

    @Provides
    @Singleton
    fun provideLoggingTree(
        isDebug: IsDebug
    ): Timber.Tree {
        return if (isDebug.exec()) {
            Timber.DebugTree()
        } else {
            // Implement and return release tree in future
            Timber.DebugTree()
        }
    }

    @Provides
    @Singleton
    @Named(DISPATCHER_IO)
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Singleton
    @Named(DISPATCHER_DEFAULT)
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default
}
