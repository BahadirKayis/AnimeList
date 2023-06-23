package com.bahadir.animelist.presentation.seeall

import com.bahadir.animelist.presentation.base.ees.Effect

sealed class SeeAllAnimeUIEffect : Effect {
    object BackPressed : SeeAllAnimeUIEffect()
    data class ActionAnimeDetail(val id: Int) : SeeAllAnimeUIEffect()
    data class SnackBarMessage(val message: String) : SeeAllAnimeUIEffect()
    data class ActionTrailer(val trailerId: String) : SeeAllAnimeUIEffect()

}