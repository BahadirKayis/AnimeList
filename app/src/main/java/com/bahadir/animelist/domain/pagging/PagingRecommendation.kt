package com.bahadir.animelist.domain.pagging

import com.bahadir.animelist.domain.mapper.recommendationsUI
import com.bahadir.animelist.domain.model.home.RecommendationsUI
import com.bahadir.animelist.domain.source.AnimeDataSource

class PagingRecommendation(
    private val animeSource: AnimeDataSource,
) : BasePaging<RecommendationsUI>() {
    override suspend fun fetchData(page: Int): List<RecommendationsUI> {
        val response = animeSource.getRecommendations(page).data
        val modelList = response.flatMap { it.entry.recommendationsUI() }
        // remove duplicate id
        return modelList.groupBy { it.id }.mapValues { it.value.first() }.values.toList()
    }
}