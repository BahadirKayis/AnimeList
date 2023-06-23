package com.bahadir.animelist.domain.usecase.home

import com.bahadir.animelist.common.Resource
import com.bahadir.animelist.domain.model.home.AnimeUI
import com.bahadir.animelist.domain.model.home.CharactersUI
import com.bahadir.animelist.domain.model.home.SeasonNowUI
import com.bahadir.animelist.domain.repository.AnimeRepository
import com.bahadir.animelist.domain.repository.CharacterRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import retrofit2.HttpException
import javax.inject.Inject

class CharacterAnimeSeasonUseCase @Inject constructor(
    private val animeService: AnimeRepository,
    private val characterRepo: CharacterRepository,
) {
    operator fun invoke(): Flow<Resource<Triple<List<CharactersUI>, List<AnimeUI>, List<SeasonNowUI>>>> =
        callbackFlow {
            try {
                val topCharacters = characterRepo.getTopCharacters()
                val topAnime = animeService.getTopAnime()
                val seasonNow = animeService.getSeasonNow()

                combine(
                    topCharacters,
                    topAnime,
                    seasonNow,
                ) { characters, anime, season ->
                    when {
                        characters is Resource.Success
                                && anime is Resource.Success
                                && season is Resource.Success -> {

                            trySend(
                                Resource.Success(
                                    Triple(characters.data, anime.data, season.data)
                                )
                            )
                        }

                        characters is Resource.Error -> {
                            trySend(Resource.Error("Characters Error: " + characters.message))
                        }

                        anime is Resource.Error -> {
                            trySend(Resource.Error("Top Anime Error: " + anime.message))
                        }

                        season is Resource.Error -> {
                            trySend(Resource.Error("Recommended Error: " + season.message))
                        }

                        else -> {

                        }
                    }
                }.collect()

            } catch (e: HttpException) {
                trySend(Resource.Error(e.message ?: "An error occurred"))
            }
            awaitClose { channel.close() }
        }
}
