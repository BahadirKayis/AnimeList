package com.bahadir.animelist.domain.network

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Build
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

class NetworkObserver @Inject constructor (
    private val conn: ConnectivityManager,
    private val request: NetworkRequest
) {

    fun observe(): Flow<NetworkStatus> = callbackFlow {
        val callBack = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                launch { send(element = NetworkStatus.AVAILABLE) }
            }

            override fun onUnavailable() {
                launch { send(element = NetworkStatus.UNAVAILABLE) }
            }

            override fun onLosing(network: Network, maxMsToLive: Int) {
                launch { send(element = NetworkStatus.LOSING) }
            }

            override fun onLost(network: Network) {
                launch { send(element = NetworkStatus.LOST) }
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            conn.registerDefaultNetworkCallback(callBack)
        }else {
            conn.registerNetworkCallback(request, callBack)
        }

        awaitClose { conn.unregisterNetworkCallback(callBack) }

    }.distinctUntilChanged()

}