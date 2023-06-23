package com.bahadir.animelist.di

import androidx.fragment.app.Fragment
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@InstallIn(FragmentComponent::class)
@Module
object LifeCycleModule {
    @Provides
    @FragmentScoped
    fun provideLifeCycle() = Fragment().lifecycle
}