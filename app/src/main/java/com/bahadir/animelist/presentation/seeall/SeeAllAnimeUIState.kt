package com.bahadir.animelist.presentation.seeall


import androidx.paging.PagingData
import com.bahadir.animelist.domain.model.home.AnimeUI
import com.bahadir.animelist.domain.model.home.RecommendationsUI
import com.bahadir.animelist.domain.model.home.SeasonNowUI
import com.bahadir.animelist.presentation.base.ees.State

data class SeeAllAnimeUIState(
    val isLoading: Boolean = false,
    val topAnime: PagingData<AnimeUI>? = null,
    val seasonNow: PagingData<SeasonNowUI>? = null,
    val recommendation: PagingData<RecommendationsUI>? = null
) : State