package com.bahadir.animelist.presentation.search

import com.bahadir.animelist.presentation.base.ees.Event

sealed class SearchUIEvent : Event {
    object BackPressed : SearchUIEvent()
    data class ActionAnimeDetail(val id: Int) : SearchUIEvent()
    object ShowFilter : SearchUIEvent()
    data class Search(val query: String) : SearchUIEvent()
}