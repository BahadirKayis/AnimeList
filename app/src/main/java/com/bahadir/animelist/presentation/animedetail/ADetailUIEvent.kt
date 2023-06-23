package com.bahadir.animelist.presentation.animedetail

import com.bahadir.animelist.presentation.base.ees.Event

sealed class ADetailUIEvent : Event {
    object BackPressed : ADetailUIEvent()
    data class CharacterFilter(val filter: String) : ADetailUIEvent()
    object ActionPlayVideo : ADetailUIEvent()
    data class CharacterDetailUI(val id: Int) : ADetailUIEvent()
    data class ActionAnimeDetailUI(val id: Int) : ADetailUIEvent()
    object ActionWeb : ADetailUIEvent()
    object ActionSend : ADetailUIEvent()
}