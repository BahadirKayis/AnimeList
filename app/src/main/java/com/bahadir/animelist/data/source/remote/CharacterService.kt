package com.bahadir.animelist.data.source.remote

import com.bahadir.animelist.common.Constants.ANIME_CHARACTER
import com.bahadir.animelist.common.Constants.CHARACTER_ANIME
import com.bahadir.animelist.common.Constants.CHARACTER_INFO
import com.bahadir.animelist.common.Constants.CHARACTER_PICTURES
import com.bahadir.animelist.common.Constants.TOP_CHARACTERS
import com.bahadir.animelist.data.model.animeCharacter.AnimeCharacterNetwork
import com.bahadir.animelist.data.model.character.CharacterAnimeDataNetwork
import com.bahadir.animelist.data.model.character.CharacterDataNetwork
import com.bahadir.animelist.data.model.character.Characters
import com.bahadir.animelist.data.model.character.CharterPicturesNetwork
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharacterService {
    @GET(TOP_CHARACTERS)
    suspend fun getTopCharacters(@Query("limit") limit: Int = 10): Characters

    @GET(TOP_CHARACTERS)
    suspend fun getTopCharactersPage(@Query("page") limit: Int): Characters

    @GET(CHARACTER_INFO)
    suspend fun getCharacter(@Path("id") id: Int): CharacterDataNetwork

    @GET(CHARACTER_ANIME)
    suspend fun getCharacterAnime(@Path("id") id: Int): CharacterAnimeDataNetwork

    @GET(CHARACTER_PICTURES)
    suspend fun getCharacterPictures(@Path("id") id: Int): CharterPicturesNetwork

    @GET(ANIME_CHARACTER)
    suspend fun getAnimeCharacter(@Path("id") id: Int): AnimeCharacterNetwork

}