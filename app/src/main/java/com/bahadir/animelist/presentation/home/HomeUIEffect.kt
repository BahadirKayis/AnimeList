package com.bahadir.animelist.presentation.home

import com.bahadir.animelist.domain.model.home.CharactersUI
import com.bahadir.animelist.presentation.base.ees.Effect

sealed class HomeUIEffect : Effect {
    data class SnackBarMessage(val message: String) : HomeUIEffect()
    data class ActionCharacterDetail(val characters: CharactersUI) : HomeUIEffect()
    data class ActionAnimeDetail(val id: Int) : HomeUIEffect()
    object ActionTopAnime : HomeUIEffect()
    object ActionSeasonNow : HomeUIEffect()
    object ActionRecommendation : HomeUIEffect()

}