package com.bahadir.animelist.presentation.animedetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bahadir.animelist.common.Resource
import com.bahadir.animelist.common.extensions.collectIn
import com.bahadir.animelist.delegation.viewmodel.VMDelegation
import com.bahadir.animelist.delegation.viewmodel.VMDelegationImpl
import com.bahadir.animelist.domain.usecase.animedetail.AnimeDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ADetailVM @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val useCase: AnimeDetailUseCase,
  private val dispatcherIO: CoroutineDispatcher
) : ViewModel(), VMDelegation<ADetailUIEffect, ADetailUIEvent, ADetailUIState>
by VMDelegationImpl(ADetailUIState(true)) {

    init {
        viewModel(this)
        event.collectIn(viewModelScope) { event ->
            when (event) {
                is ADetailUIEvent.CharacterFilter -> {
                    setEffect(ADetailUIEffect.CharacterFilter(event.filter))
                }

                is ADetailUIEvent.ActionPlayVideo -> {
                    getCurrentState().animData?.videoUrl?.let {
                        setEffect(ADetailUIEffect.ActionPlayVideo(it))
                    } ?: run {
                        setEffect(ADetailUIEffect.Error("Video not found"))
                    }
                }

                is ADetailUIEvent.CharacterDetailUI -> {
                    getCharacter(event.id)
                }

                is ADetailUIEvent.ActionAnimeDetailUI -> {
                    viewModelScope.launch {
                        getAnimeDetail(event.id)
                        delay(DELAY)
                        getAnimeCharacter(event.id)
                    }
                }

                ADetailUIEvent.BackPressed -> setEffect(ADetailUIEffect.BackPressed)

                is ADetailUIEvent.ActionWeb -> {
                    getCurrentState().animData?.url?.let {
                        setEffect(ADetailUIEffect.ActionWeb(it))
                    }

                }

                is ADetailUIEvent.ActionSend ->
                    getCurrentState().animData?.url?.let {
                        setEffect(ADetailUIEffect.ActionSend(it))
                    }
            }
        }
        savedStateHandle.get<Int>(KEY_ID)?.let {
            viewModelScope.launch {
                getAnime(it)
                delay(DELAY)
                getAnimeCharacter(it)
                delay(DELAY)
                getAnimeDetail(it)
            }

        }
    }

    private fun getAnimeDetail(id: Int) {
        useCase.getAnimePhoto(id).flowOn(dispatcherIO).onEach {
            when (it) {
                is Resource.Success -> {
                    val data = it.data
                    setState(
                        getCurrentState().copy(
                            isLoading = false,
                            animData = data.first,
                            images = data.second
                        )
                    )
                }

                is Resource.Error -> {
                    setEffect(ADetailUIEffect.Error(it.message))
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getAnime(id: Int) {
        useCase.getOtherInformation(id).flowOn(dispatcherIO).onEach {
            when (it) {
                is Resource.Success -> {
                    setState(
                        getCurrentState().copy(
                            recommendation = it.data.first,
                            comments = it.data.second,
                        )
                    )
                }

                is Resource.Error -> {
                    setEffect(ADetailUIEffect.Error(it.message))
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getAnimeCharacter(id: Int) {
        useCase.getCharactersAnimeDetail(id).flowOn(dispatcherIO).onEach {
            when (it) {
                is Resource.Success -> {
                    setState(getCurrentState().copy(character = it.data))
                }

                is Resource.Error -> {
                    setEffect(ADetailUIEffect.Error(it.message))
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getCharacter(id: Int) {
        useCase.getCharacter.invoke(id).flowOn(dispatcherIO).onEach {
            when (it) {
                is Resource.Success -> {
                    setEffect(ADetailUIEffect.ActionCharacterDetail(it.data))
                }

                is Resource.Error -> {
                    setEffect(ADetailUIEffect.Error(it.message))
                }
            }
        }.launchIn(viewModelScope)
    }

    companion object {
        private const val KEY_ID = "id"
        private const val DELAY = 1200L
    }

}