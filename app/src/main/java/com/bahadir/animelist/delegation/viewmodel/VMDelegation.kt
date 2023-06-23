package com.bahadir.animelist.delegation.viewmodel

import androidx.lifecycle.ViewModel
import com.bahadir.animelist.presentation.base.ees.Effect
import com.bahadir.animelist.presentation.base.ees.Event
import com.bahadir.animelist.presentation.base.ees.State
import kotlinx.coroutines.flow.SharedFlow

interface VMDelegation<EFFECT : Effect, EVENT : Event, STATE : State> {
    fun viewModel(viewModel: ViewModel)
    fun setEffect(effect: EFFECT)
    fun setEvent(event: EVENT)
    fun setState(state: STATE)
    fun getCurrentState(): STATE

    val effect: SharedFlow<EFFECT>
    val event: SharedFlow<EVENT>
    val state: SharedFlow<STATE>

}