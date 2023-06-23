package com.bahadir.animelist.data.source.remote

import com.bahadir.animelist.data.model.animeCharacter.AnimeCharacterNetwork
import com.bahadir.animelist.data.model.character.CharacterAnimeDataNetwork
import com.bahadir.animelist.data.model.character.CharacterData
import com.bahadir.animelist.data.model.character.Characters
import com.bahadir.animelist.domain.source.CharacterDataSource

class CharacterDataSourceImpl(
    private val characterService: CharacterService
) : CharacterDataSource {
    override suspend fun getTopCharacters(): Characters = characterService.getTopCharacters()
    override suspend fun getTopCharactersPage(page: Int): Characters =
        characterService.getTopCharactersPage(page)

    override suspend fun getAnimeCharacter(id: Int): AnimeCharacterNetwork =
        characterService.getAnimeCharacter(id)

    override suspend fun getCharacter(id: Int): CharacterData =
        characterService.getCharacter(id).data

    override suspend fun getCharacterAnime(id: Int): CharacterAnimeDataNetwork =
        characterService.getCharacterAnime(id)

    override suspend fun getCharacterPhoto(id: Int) = characterService.getCharacterPictures(id)


}