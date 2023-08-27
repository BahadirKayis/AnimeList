package com.bahadir.animelist.di

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkRequest
import com.bahadir.animelist.domain.network.NetworkObserver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@[InstallIn(SingletonComponent::class) Module]
object NetworkModule {

    @[Singleton Provides]
    fun provideNetworkObserver(conn: ConnectivityManager, request: NetworkRequest): NetworkObserver = NetworkObserver(conn = conn, request = request)

    @[Singleton Provides]
    fun provideConnManager(@ApplicationContext context: Context): ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    @[Singleton Provides]
    fun provideNetworkRequest(): NetworkRequest = NetworkRequest.Builder().build()

}