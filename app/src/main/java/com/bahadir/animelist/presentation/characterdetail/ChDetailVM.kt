package com.bahadir.animelist.presentation.characterdetail

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bahadir.animelist.common.Resource
import com.bahadir.animelist.common.extensions.collectIn
import com.bahadir.animelist.delegation.viewmodel.VMDelegation
import com.bahadir.animelist.delegation.viewmodel.VMDelegationImpl
import com.bahadir.animelist.domain.model.CharacterAbout
import com.bahadir.animelist.domain.model.home.CharactersUI
import com.bahadir.animelist.domain.usecase.character.CharacterPhotoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ChDetailVM @Inject constructor(
    savedStateHandle: SavedStateHandle, private val characterInfo: CharacterPhotoUseCase
) : ViewModel(), VMDelegation<ChDetailUIEffect, ChDetailUIEvent, ChDetailUIState>
by VMDelegationImpl(ChDetailUIState(isLoading = true)) {
    init {
        viewModel(this)
        event.collectIn(viewModelScope) { event ->
            when (event) {
                is ChDetailUIEvent.BackPressed -> {
                    setEffect(ChDetailUIEffect.GoBack)
                }

                is ChDetailUIEvent.ActionWebPage -> {
                    setEffect(ChDetailUIEffect.ActionWebPage(Uri.parse(event.url)))
                }

                is ChDetailUIEvent.ActionAnimeDetailUI -> {
                    setEffect(ChDetailUIEffect.ActionAnimeDetail(event.id))
                }
            }
        }
        savedStateHandle.get<CharactersUI>(KEY_CHARACTER)?.let {
            getCharacterInfo(it)
        }
    }

    private fun getCharacterInfo(character: CharactersUI) {
        characterInfo.invoke(character.id).onEach {
            when (it) {
                is Resource.Success -> {
                    setState(
                        ChDetailUIState(
                            isLoading = false,
                            character,
                            it.data.second,
                            it.data.first,
                            convertAbout(character.about)
                        )
                    )
                }

                is Resource.Error -> {
                    setEffect(ChDetailUIEffect.Error(it.message))
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun convertAbout(it: String): List<CharacterAbout> {
        val data: MutableList<CharacterAbout> = mutableListOf()
        val list = it.split("\n")
        var about = " "
        for (i in list) {
            val keyValue = i.split(":")
            if (keyValue.size == 2) {
                if (keyValue[1].isNotBlank()) {
                    data.add(CharacterAbout(keyValue[0], keyValue[1]))
                }
            } else {
                if (i.isNotBlank()) {
                    about += i
                }
            }
        }
        data.add(CharacterAbout("About", about))
        return data
    }


    companion object {
        private const val KEY_CHARACTER = "character"
    }
}