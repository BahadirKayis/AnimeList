package com.bahadir.animelist.presentation.characterdetail

import com.bahadir.animelist.presentation.base.ees.Event

sealed class ChDetailUIEvent : Event {
    object BackPressed : ChDetailUIEvent()
    data class ActionWebPage(val url: String) : ChDetailUIEvent()
    data class ActionAnimeDetailUI(val id: Int) : ChDetailUIEvent()

}