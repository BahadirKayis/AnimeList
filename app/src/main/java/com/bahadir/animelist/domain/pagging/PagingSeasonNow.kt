package com.bahadir.animelist.domain.pagging

import com.bahadir.animelist.domain.mapper.seasonNowUI
import com.bahadir.animelist.domain.model.home.SeasonNowUI
import com.bahadir.animelist.domain.source.AnimeDataSource

class PagingSeasonNow(
    private val animeSource: AnimeDataSource,
) : BasePaging<SeasonNowUI>() {
    override suspend fun fetchData(page: Int): List<SeasonNowUI> {
        val response = animeSource.getSeasonNow(page).data
        return response.seasonNowUI()
    }

}