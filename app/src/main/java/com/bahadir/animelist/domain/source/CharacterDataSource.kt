package com.bahadir.animelist.domain.source

import com.bahadir.animelist.data.model.animeCharacter.AnimeCharacterNetwork
import com.bahadir.animelist.data.model.character.CharacterAnimeDataNetwork
import com.bahadir.animelist.data.model.character.CharacterData
import com.bahadir.animelist.data.model.character.Characters
import com.bahadir.animelist.data.model.character.CharterPicturesNetwork

interface CharacterDataSource {
    suspend fun getCharacterAnime(id: Int): CharacterAnimeDataNetwork
    suspend fun getCharacterPhoto(id: Int): CharterPicturesNetwork
    suspend fun getTopCharacters(): Characters
    suspend fun getTopCharactersPage(page: Int): Characters
    suspend fun getAnimeCharacter(id: Int): AnimeCharacterNetwork
    suspend fun getCharacter(id: Int): CharacterData
}