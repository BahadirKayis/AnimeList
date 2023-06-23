package com.bahadir.animelist.data.repository

import com.bahadir.animelist.common.Resource
import com.bahadir.animelist.domain.mapper.animeCharacterUI
import com.bahadir.animelist.domain.mapper.characterAnimeUI
import com.bahadir.animelist.domain.mapper.charactersUI
import com.bahadir.animelist.domain.mapper.topCharactersUI
import com.bahadir.animelist.domain.model.detail.AnimeCharacterUI
import com.bahadir.animelist.domain.model.home.AnimeUI
import com.bahadir.animelist.domain.model.home.CharactersUI
import com.bahadir.animelist.domain.repository.CharacterRepository
import com.bahadir.animelist.domain.source.CharacterDataSource
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import retrofit2.HttpException

class CharacterRepositoryImpl(private val characterSource: CharacterDataSource) :
    CharacterRepository {
    override fun getTopCharacters(): Flow<Resource<List<CharactersUI>>> = callbackFlow {
        try {
            trySend(Resource.Success(characterSource.getTopCharacters().data.topCharactersUI()))
        } catch (e: HttpException) {
            trySend(Resource.Error(e.message.toString()))
        }
        awaitClose { channel.close() }
    }

    override fun getAnimeCharacter(id: Int): Flow<Resource<List<AnimeCharacterUI>>> =
        callbackFlow {
            try {
                val data = characterSource.getAnimeCharacter(id).data.animeCharacterUI()
                trySend(Resource.Success(data))
            } catch (e: HttpException) {
                trySend(Resource.Error(e.message.toString()))
            }
            awaitClose { channel.close() }

        }

    override fun getCharacterPhotos(id: Int): Flow<Resource<List<String>>> = callbackFlow {
        try {
            trySend(Resource.Success(characterSource.getCharacterPhoto(id).data.map { it.jpg.imageUrl }))
        } catch (e: HttpException) {
            trySend(Resource.Error(e.message.toString()))
        }
        awaitClose { channel.close() }
    }

    override fun getCharacterAnime(id: Int): Flow<Resource<List<AnimeUI>>> =
        callbackFlow {
            try {
                trySend(
                    Resource.Success(characterSource.getCharacterAnime(id).data
                        .map { it.characterAnime.characterAnimeUI() }
                    )
                )
            } catch (e: HttpException) {
                trySend(Resource.Error(e.message.toString()))

            }
            awaitClose { channel.close() }
        }

    override fun getCharacter(id: Int): Flow<Resource<CharactersUI>> =
        callbackFlow {
            try {
                val data = characterSource.getCharacter(id).charactersUI()
                trySend(Resource.Success(data))
            } catch (e: HttpException) {
                trySend(Resource.Error(e.message.toString()))
            }
            awaitClose { channel.close() }
        }

}