package com.bahadir.animelist.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object CoroutineModule {
    @Provides
    @Singleton
    fun provideCoroutineDispatcherProvider(): CoroutineDispatcher = Dispatchers.IO

    @Named(value = "Unconfined")
    @[Provides Singleton]
    fun provideNetworkDispatcher(): CoroutineDispatcher = Dispatchers.Unconfined
}
