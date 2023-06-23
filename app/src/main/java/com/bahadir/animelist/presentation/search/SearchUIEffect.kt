package com.bahadir.animelist.presentation.search

import com.bahadir.animelist.presentation.base.ees.Effect

sealed class SearchUIEffect : Effect {
    object BackPressed : SearchUIEffect()
    data class ActionAnimeDetail(val id: Int) : SearchUIEffect()
    data class SnackBarMessage(val message: String) : SearchUIEffect()

}