package com.bahadir.animelist.delegation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bahadir.animelist.presentation.base.ees.Effect
import com.bahadir.animelist.presentation.base.ees.Event
import com.bahadir.animelist.presentation.base.ees.State
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class VMDelegationImpl<EFFECT : Effect, EVENT : Event, STATE : State>(
    setInitialState: STATE
) : VMDelegation<EFFECT, EVENT, STATE> {
    private lateinit var viewModel: ViewModel
    private val _effectTemp = MutableSharedFlow<EFFECT>()
    override val effect: SharedFlow<EFFECT>
        get() = _effectTemp.asSharedFlow()

    private val _eventTemp = MutableSharedFlow<EVENT>()
    override val event: SharedFlow<EVENT>
        get() = _eventTemp.asSharedFlow()

    private val _stateTemp = MutableStateFlow(setInitialState)
    override val state: SharedFlow<STATE>
        get() = _stateTemp.asSharedFlow()


    override fun viewModel(viewModel: ViewModel) {
        this.viewModel = viewModel
    }

    override fun getCurrentState() = _stateTemp.value
    override fun setState(state: STATE) {
        viewModel.viewModelScope.launch {
            _stateTemp.emit(state)
        }
    }

    override fun setEvent(event: EVENT) {
        viewModel.viewModelScope.launch {
            _eventTemp.emit(event)
        }
    }

    override fun setEffect(effect: EFFECT) {
        viewModel.viewModelScope.launch {
            _effectTemp.emit(effect)
        }
    }


}