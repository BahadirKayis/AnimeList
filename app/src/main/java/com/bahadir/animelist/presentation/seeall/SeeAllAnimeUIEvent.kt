package com.bahadir.animelist.presentation.seeall

import com.bahadir.animelist.presentation.base.ees.Event

sealed class SeeAllAnimeUIEvent : Event {
    object BackPressed : SeeAllAnimeUIEvent()
    data class ActionAnimeDetailUI(val id: Int) : SeeAllAnimeUIEvent()
    data class ActionTrailer(val trailerId: String?) : SeeAllAnimeUIEvent()
}