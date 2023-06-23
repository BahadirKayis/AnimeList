package com.bahadir.animelist.domain.usecase.seeall

import javax.inject.Inject

data class SeeAllUseCase @Inject constructor(
    val getRecommendation: RecommendationUseCase,
    val getSeasonNow: SeasonNowUseCase,
    val getTopHitsAnime: TopHitsAnimeUseCase
)