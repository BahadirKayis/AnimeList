package com.bahadir.animelist.presentation.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.bahadir.animelist.common.extensions.collectIn
import com.bahadir.animelist.delegation.viewmodel.VMDelegation
import com.bahadir.animelist.delegation.viewmodel.VMDelegationImpl
import com.bahadir.animelist.domain.usecase.GetScheduleAnimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduleVM @Inject constructor(
    private val scheduleAnimeUseCase: GetScheduleAnimeUseCase,
    private val dispatcherIO: CoroutineDispatcher,
) :
    ViewModel(), VMDelegation<ScheduleUIEffect, ScheduleUIEvent, ScheduleUIState>
by VMDelegationImpl(ScheduleUIState(true)) {
    init {
        viewModel(this)
        event.collectIn(viewModelScope) { event ->
            when (event) {
                is ScheduleUIEvent.GetScheduleAnime -> {
                    setState(getCurrentState().copy(isLoading = true))
                    getScheduleAnime(event.day)
                }

                is ScheduleUIEvent.ActionPlayVideo ->
                    setEffect(ScheduleUIEffect.ActionPlayVideo(event.url))
            }
        }
        getScheduleAnime("monday")
    }

    //API de burasıda değiştirilimiş gün farketmkesizin aynı verileri gönderiyor.
    private fun getScheduleAnime(day: String) = viewModelScope.launch {
        scheduleAnimeUseCase(day).flowOn(dispatcherIO).cachedIn(viewModelScope)
            .collectLatest { result ->
                setState(ScheduleUIState(isLoading = false, anime = result))
            }
    }

}