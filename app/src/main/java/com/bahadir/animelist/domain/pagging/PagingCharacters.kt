package com.bahadir.animelist.domain.pagging

import com.bahadir.animelist.domain.mapper.topCharactersUI
import com.bahadir.animelist.domain.model.home.CharactersUI
import com.bahadir.animelist.domain.source.CharacterDataSource

class PagingCharacters(
    private val characterSource: CharacterDataSource,
) : BasePaging<CharactersUI>() {
    override suspend fun fetchData(page: Int): List<CharactersUI> {
        val response = characterSource.getTopCharactersPage(page).data
        return response.topCharactersUI()
    }

}