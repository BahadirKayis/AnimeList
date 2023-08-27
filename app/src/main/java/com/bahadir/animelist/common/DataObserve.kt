package com.bahadir.animelist.common

import com.bahadir.animelist.domain.network.NetworkStatus

class DataObserve(var executeProcess: () -> Unit, var executeProgress: () -> Unit) {

    fun observeData(status: NetworkStatus) {
        when(status) {
            NetworkStatus.AVAILABLE -> executeProcess()
            else -> executeProgress()
        }
    }

}