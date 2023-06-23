package com.bahadir.animelist.domain.usecase.animedetail

import com.bahadir.animelist.common.Resource
import com.bahadir.animelist.domain.model.detail.CommentsUI
import com.bahadir.animelist.domain.model.home.RecommendationsUI
import com.bahadir.animelist.domain.repository.AnimeRepository
import com.google.android.gms.common.api.ApiException
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class AnimeDetailCommentsMoreLikeUseCase @Inject constructor(
    private val animeRepo: AnimeRepository
) {
    operator fun invoke(id: Int): Flow<Resource<Pair<List<RecommendationsUI>, List<CommentsUI>>>> =
        callbackFlow {
            try {
                val recommendations = animeRepo.getAnimeRecommendations(id)
                val comments = animeRepo.getAnimeComments(id)

                combine(recommendations, comments) { recommendation, com ->
                    when {
                        recommendation is Resource.Success && com is Resource.Success -> {
                            trySend(Resource.Success(Pair(recommendation.data, com.data)))
                        }

                        recommendation is Resource.Error -> {
                            trySend(Resource.Error("Recommendations Error: " + recommendation.message))
                        }

                        com is Resource.Error -> {
                            trySend(Resource.Error("Character Error: " + com.message))
                        }

                        else -> {}
                    }
                }.collect()
            } catch (e: ApiException) {
                trySend(Resource.Error(e.message ?: "An error occurred"))
            }
            awaitClose { channel.close() }
        }
}
