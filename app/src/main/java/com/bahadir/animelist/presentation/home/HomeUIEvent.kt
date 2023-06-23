package com.bahadir.animelist.presentation.home

import com.bahadir.animelist.domain.model.home.CharactersUI
import com.bahadir.animelist.presentation.base.ees.Event

sealed class HomeUIEvent : Event {
    data class ActionCharacterDetail(val characters: CharactersUI) : HomeUIEvent()
    data class ActionAnimeDetail(val id: Int) : HomeUIEvent()
    data class NewEpisode(val message: String) : HomeUIEvent()
    object ActionTopAnime : HomeUIEvent()
    object ActionSeasonNow : HomeUIEvent()
    object ActionRecommendation : HomeUIEvent()

}