package com.bahadir.animelist.presentation.animedetail

import com.bahadir.animelist.domain.model.home.CharactersUI
import com.bahadir.animelist.presentation.base.ees.Effect

sealed class ADetailUIEffect : Effect {
    object BackPressed : ADetailUIEffect()
    data class CharacterFilter(val filter: String) : ADetailUIEffect()
    data class ActionPlayVideo(val videoId: String) : ADetailUIEffect()
    data class Error(val message: String) : ADetailUIEffect()
    data class ActionCharacterDetail(val character: CharactersUI) : ADetailUIEffect()
    data class ActionWeb(val url: String) : ADetailUIEffect()
    data class ActionSend(val url: String) : ADetailUIEffect()

}