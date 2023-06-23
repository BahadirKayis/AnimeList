package com.bahadir.animelist.presentation.characterdetail

import com.afdhal_fa.imageslider.model.SlideUIModel
import com.bahadir.animelist.domain.model.CharacterAbout
import com.bahadir.animelist.domain.model.home.AnimeUI
import com.bahadir.animelist.domain.model.home.CharactersUI
import com.bahadir.animelist.presentation.base.ees.State

data class ChDetailUIState(
    val isLoading: Boolean = false,
    val character: CharactersUI? = null,
    val photoUrl: List<SlideUIModel>? = null,
    val anime: List<AnimeUI>? = null,
    val about: List<CharacterAbout>? = null,
) : State