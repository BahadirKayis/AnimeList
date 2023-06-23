package com.bahadir.animelist.domain.pagging

import com.bahadir.animelist.domain.mapper.animeUI
import com.bahadir.animelist.domain.model.home.AnimeUI
import com.bahadir.animelist.domain.source.AnimeDataSource

class PagingTopAnime(
    private val animeSource: AnimeDataSource,
) : BasePaging<AnimeUI>() {
    override suspend fun fetchData(page: Int): List<AnimeUI> {
        val response = animeSource.getTopAnime(page).data
        return response.animeUI()
    }

}