package com.bahadir.animelist.presentation.animedetail

import com.afdhal_fa.imageslider.model.SlideUIModel
import com.bahadir.animelist.domain.model.AnimeDetailUI
import com.bahadir.animelist.domain.model.detail.AnimeCharacterUI
import com.bahadir.animelist.domain.model.detail.CommentsUI
import com.bahadir.animelist.domain.model.home.RecommendationsUI
import com.bahadir.animelist.presentation.base.ees.State

data class ADetailUIState(
    val isLoading: Boolean = false,
    val animData: AnimeDetailUI? = null,
    val images: List<SlideUIModel>? = null,
    val comments: List<CommentsUI>? = null,
    val recommendation: List<RecommendationsUI>? = null,
    val character: List<AnimeCharacterUI>? = null,
) : State