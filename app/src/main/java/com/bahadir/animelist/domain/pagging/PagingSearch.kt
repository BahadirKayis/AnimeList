package com.bahadir.animelist.domain.pagging

import com.bahadir.animelist.domain.mapper.animeUI
import com.bahadir.animelist.domain.model.home.AnimeUI
import com.bahadir.animelist.domain.source.AnimeDataSource

class PagingSearch(
    private val animeSource: AnimeDataSource, private val query: String
) : BasePaging<AnimeUI>() {
    override suspend fun fetchData(page: Int): List<AnimeUI> {
        val response = animeSource.getSearch(page, query).data
        return response.animeUI()
    }

}