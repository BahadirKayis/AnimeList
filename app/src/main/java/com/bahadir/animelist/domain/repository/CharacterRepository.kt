package com.bahadir.animelist.domain.repository

import com.bahadir.animelist.common.Resource
import com.bahadir.animelist.domain.model.detail.AnimeCharacterUI
import com.bahadir.animelist.domain.model.home.AnimeUI
import com.bahadir.animelist.domain.model.home.CharactersUI
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    fun getCharacterAnime(id: Int): Flow<Resource<List<AnimeUI>>>
    fun getCharacterPhotos(id: Int): Flow<Resource<List<String>>>
    fun getTopCharacters(): Flow<Resource<List<CharactersUI>>>
    fun getAnimeCharacter(id: Int): Flow<Resource<List<AnimeCharacterUI>>>
    fun getCharacter(id: Int): Flow<Resource<CharactersUI>>
}