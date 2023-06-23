package com.bahadir.animelist.di

import android.content.Context
import com.bahadir.animelist.data.repository.FirebaseAuthenticator

import com.bahadir.animelist.domain.repository.Authenticator
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object AuthenticatorModule {
    @Provides
    @Singleton
    fun provideAuthenticator(
        firebaseAuth: FirebaseAuth,
        @ApplicationContext context: Context,
    ): Authenticator =
        FirebaseAuthenticator(firebaseAuth, context)
}