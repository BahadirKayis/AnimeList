package com.bahadir.animelist.presentation.home


import com.bahadir.animelist.domain.model.home.AnimeUI
import com.bahadir.animelist.domain.model.home.CharactersUI
import com.bahadir.animelist.domain.model.home.EpisodePopularUI
import com.bahadir.animelist.domain.model.home.RecommendationsUI
import com.bahadir.animelist.domain.model.home.SeasonNowUI
import com.bahadir.animelist.presentation.base.ees.State

data class HomeUIState(
    val isLoading: Boolean = false,
    val characters: List<CharactersUI>? = null,
    val anime: List<AnimeUI>? = null,
    val season: List<SeasonNowUI>? = null,
    val episodes: List<EpisodePopularUI>? = null,
    val recommendations: List<RecommendationsUI>? = null
) : State
