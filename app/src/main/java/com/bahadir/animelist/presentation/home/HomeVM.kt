package com.bahadir.animelist.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bahadir.animelist.common.DataObserve
import com.bahadir.animelist.common.Resource
import com.bahadir.animelist.common.extensions.collectIn
import com.bahadir.animelist.delegation.viewmodel.VMDelegation
import com.bahadir.animelist.delegation.viewmodel.VMDelegationImpl
import com.bahadir.animelist.domain.network.NetworkObserver
import com.bahadir.animelist.domain.usecase.home.CharacterAnimeSeasonUseCase
import com.bahadir.animelist.domain.usecase.home.EpisodeRandomRecommendationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class HomeVM @Inject constructor(
    private val getCharacterAnimSeason: CharacterAnimeSeasonUseCase,
    private val dispatcherIO: CoroutineDispatcher,
    private val getSecondData: EpisodeRandomRecommendationUseCase,
    networkObserver: NetworkObserver,
    @Named(value = "Unconfined") private val dispatcherUnconfined: CoroutineDispatcher
) : ViewModel(),
    VMDelegation<HomeUIEffect, HomeUIEvent, HomeUIState> by VMDelegationImpl(HomeUIState(isLoading = true)) {
    private val dataObserve = DataObserve(
        executeProcess = {
            getFirstData()
        },
        executeProgress = {
            setState(state = HomeUIState(isLoading = true))
        }
    )

    init {
        viewModel(this)
        event.collectIn(viewModelScope) { event ->
            when (event) {
                is HomeUIEvent.ActionAnimeDetail -> setEffect(HomeUIEffect.ActionAnimeDetail(event.id))

                is HomeUIEvent.ActionCharacterDetail ->
                    setEffect(HomeUIEffect.ActionCharacterDetail(event.characters))

                is HomeUIEvent.ActionTopAnime -> setEffect(HomeUIEffect.ActionTopAnime)

                is HomeUIEvent.ActionSeasonNow -> setEffect(HomeUIEffect.ActionSeasonNow)

                is HomeUIEvent.ActionRecommendation -> setEffect(HomeUIEffect.ActionRecommendation)
                is HomeUIEvent.NewEpisode -> setEffect(HomeUIEffect.SnackBarMessage(event.message))
            }
        }

        networkObserver.observe().flowOn(context = dispatcherUnconfined).collectIn(coroutineScope = viewModelScope, function = dataObserve::observeData)
    }

    //flowOn(dispatcherIO) IO işlemlerini ayrı bir thread üzerinde çalıştırırken,
    //launchIn(viewModelScope) ise Flow'u ViewModel'in kapsamında yönetir
    private fun getFirstData() {
        getCharacterAnimSeason.invoke().flowOn(dispatcherIO).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    setState(
                        getCurrentState().copy(
                            isLoading = true,
                            characters = result.data.first,
                            anime = result.data.second,
                            season = result.data.third
                        )
                    )
                    delay(DELAY)//Rate limit prevention with delay.
                    getSecondData()
                }

                is Resource.Error -> {
                    setEffect(HomeUIEffect.SnackBarMessage(result.message))
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getSecondData() {
        getSecondData.invoke().flowOn(dispatcherIO).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    setState(
                        getCurrentState().copy(
                            isLoading = false,
                            episodes = result.data.first,
                            recommendations = result.data.second
                        )
                    )
                }

                is Resource.Error -> {
                    setEffect(HomeUIEffect.SnackBarMessage(result.message))
                }
            }
        }.launchIn(viewModelScope)

    }

    companion object {
        private const val DELAY = 2000L
    }
}