package com.bahadir.animelist.presentation.seeall

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.bahadir.animelist.common.SeeAllAnime
import com.bahadir.animelist.common.extensions.collectIn
import com.bahadir.animelist.delegation.viewmodel.VMDelegation
import com.bahadir.animelist.delegation.viewmodel.VMDelegationImpl
import com.bahadir.animelist.domain.usecase.seeall.SeeAllUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeeAllAnimeVM @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val dispatcherIO: CoroutineDispatcher,
    private val useCase: SeeAllUseCase,
) : ViewModel(),
    VMDelegation<SeeAllAnimeUIEffect, SeeAllAnimeUIEvent, SeeAllAnimeUIState> by VMDelegationImpl(
        SeeAllAnimeUIState(isLoading = true)
    ) {
    init {
        viewModel(this)
        event.collectIn(viewModelScope) { event ->
            when (event) {
                is SeeAllAnimeUIEvent.BackPressed -> {
                    setEffect(SeeAllAnimeUIEffect.BackPressed)
                }

                is SeeAllAnimeUIEvent.ActionAnimeDetailUI -> {
                    setEffect(SeeAllAnimeUIEffect.ActionAnimeDetail(event.id))
                }

                is SeeAllAnimeUIEvent.ActionTrailer -> {
                    event.trailerId?.let {
                        setEffect(SeeAllAnimeUIEffect.ActionTrailer(it))
                    } ?: run {
                        setEffect(SeeAllAnimeUIEffect.SnackBarMessage("Video not found"))
                    }

                }
            }
        }
        savedStateHandle.get<SeeAllAnime>(KEY_ANIME)?.let {
            when (it) {
                SeeAllAnime.TOP_ANIME -> topAnime()
                SeeAllAnime.SEASON_NOW -> seasonNow()
                SeeAllAnime.RECOMMENDATION -> recommendation()
            }
        }
    }

    private fun topAnime() = viewModelScope.launch {
        useCase.getTopHitsAnime.invoke().flowOn(dispatcherIO).cachedIn(viewModelScope)
            .collectLatest {
                setState(SeeAllAnimeUIState(isLoading = false, topAnime = it))
            }
    }

    private fun seasonNow() = viewModelScope.launch {
        useCase.getSeasonNow.invoke().flowOn(dispatcherIO).cachedIn(viewModelScope).collect {
            setState(SeeAllAnimeUIState(isLoading = false, seasonNow = it))
        }
    }

    private fun recommendation() = viewModelScope.launch {
        useCase.getRecommendation.invoke().flowOn(dispatcherIO).cachedIn(viewModelScope).collect {
            setState(SeeAllAnimeUIState(false, recommendation = it))
        }
    }

    companion object {
        const val KEY_ANIME = "anime"
    }
}