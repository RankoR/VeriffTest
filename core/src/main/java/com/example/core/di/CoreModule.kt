package com.example.core.di

import com.example.core.domain.IsDebug
import com.example.core.domain.IsDebugImpl
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import timber.log.Timber
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CoreModule {

    @Provides
    @Reusable
    fun provideIsDebug(): IsDebug = IsDebugImpl()

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
