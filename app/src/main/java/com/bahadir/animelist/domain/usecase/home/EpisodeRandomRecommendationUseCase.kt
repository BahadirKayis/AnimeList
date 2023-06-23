package com.bahadir.animelist.domain.usecase.home

import com.bahadir.animelist.common.Resource
import com.bahadir.animelist.domain.model.home.EpisodePopularUI
import com.bahadir.animelist.domain.model.home.RecommendationsUI
import com.bahadir.animelist.domain.repository.AnimeRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import retrofit2.HttpException
import javax.inject.Inject

class EpisodeRandomRecommendationUseCase @Inject constructor(
    private val animeRepo: AnimeRepository
) {
    operator fun invoke(): Flow<Resource<Pair<List<EpisodePopularUI>, List<RecommendationsUI>>>> =
        callbackFlow {
            try {
                val episodePopular = animeRepo.getEpisodePopular()
                val recommendations = animeRepo.getRecommendations()

                combine(
                    episodePopular,
                    recommendations,
                ) { episode, recommendation ->
                    when {
                        episode is Resource.Success && recommendation is Resource.Success -> {
                            trySend(
                                Resource.Success(
                                    Pair(
                                        episode.data, recommendation.data
                                    )
                                )
                            )
                        }

                        episode is Resource.Error -> {
                            trySend(Resource.Error("Episode Error: " + episode.message))
                        }

                        recommendation is Resource.Error -> {
                            trySend(Resource.Error("Recommendation Now Error: " + recommendation.message))
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