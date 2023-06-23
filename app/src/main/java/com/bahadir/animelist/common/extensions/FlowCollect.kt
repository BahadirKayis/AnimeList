package com.bahadir.animelist.common.extensions

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


fun <T> Flow<T>.collectIn(viewLifecycleOwner: LifecycleOwner, response: (T) -> Unit) {
    viewLifecycleOwner.apply {
        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                collect {
                    response(it)
                }
            }
        }
    }

}

fun <T> Flow<T>.collectIn(coroutineScope: CoroutineScope, function: (T) -> Unit) {
    coroutineScope.launch {
        collect {
            function(it)
        }
    }
}
