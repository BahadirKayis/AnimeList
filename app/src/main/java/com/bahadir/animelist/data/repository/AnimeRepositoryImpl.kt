package com.bahadir.animelist.data.repository

import android.content.Context
import com.afdhal_fa.imageslider.model.SlideUIModel
import com.bahadir.animelist.common.Resource
import com.bahadir.animelist.domain.mapper.animeDetailUI
import com.bahadir.animelist.domain.mapper.animeUI
import com.bahadir.animelist.domain.mapper.commentsUI
import com.bahadir.animelist.domain.mapper.episodePopularUI
import com.bahadir.animelist.domain.mapper.recommendationsUI
import com.bahadir.animelist.domain.mapper.seasonNowUI
import com.bahadir.animelist.domain.model.AnimeDetailUI
import com.bahadir.animelist.domain.model.detail.CommentsUI
import com.bahadir.animelist.domain.model.home.AnimeUI
import com.bahadir.animelist.domain.model.home.EpisodePopularUI
import com.bahadir.animelist.domain.model.home.RecommendationsUI
import com.bahadir.animelist.domain.model.home.SeasonNowUI
import com.bahadir.animelist.domain.repository.AnimeRepository
import com.bahadir.animelist.domain.source.AnimeDataSource
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import retrofit2.HttpException

class AnimeRepositoryImpl(private val animeSource: AnimeDataSource, private val context: Context) :
    AnimeRepository {
    override fun getAnime(id: Int): Flow<Resource<AnimeDetailUI>> = callbackFlow {
        try {
            val data = animeSource.getAnime(id).data.animeDetailUI()
            trySend(Resource.Success(data))
        } catch (e: HttpException) {
            trySend(Resource.Error(e.message.toString()))
        } catch (e: Exception) {
            trySend(Resource.Error(e.message.toString()))
        }
        awaitClose { channel.close() }
    }

    override fun getAnimeRecommendations(id: Int): Flow<Resource<List<RecommendationsUI>>> =
        callbackFlow {
            try {
                val data = animeSource.getAnimeRecommendations(id).data
                trySend(Resource.Success(data.map { it.entry.recommendationsUI() }))
            } catch (e: HttpException) {
                trySend(Resource.Error(e.message.toString()))
            } catch (e: Exception) {
                trySend(Resource.Error(e.message.toString()))
            }
            awaitClose { channel.close() }
        }

    override fun getAnimePhotos(id: Int): Flow<Resource<List<SlideUIModel>>> = callbackFlow {
        try {
            val data = animeSource.getAnimePhotos(id).data
            val imageConvert = data.map { image ->
                SlideUIModel(
                    image.jpg.largeImageUrl ?: image.jpg.imageUrl, ""
                )
            }
            trySend(Resource.Success(imageConvert))
        } catch (e: HttpException) {
            trySend(Resource.Error(e.message.toString()))
        } catch (e: Exception) {
            trySend(Resource.Error(e.message.toString()))
        }
        awaitClose { channel.close() }
    }

    override fun getAnimeComments(id: Int): Flow<Resource<List<CommentsUI>>> = callbackFlow {
        try {
            val data = animeSource.getAnimeComments(id).data
            trySend(Resource.Success(data.commentsUI(context)))
        } catch (e: HttpException) {
            trySend(Resource.Error(e.message.toString()))
        } catch (e: Exception) {
            trySend(Resource.Error(e.message.toString()))
        }
        awaitClose { channel.close() }
    }

    override fun getTopAnime(): Flow<Resource<List<AnimeUI>>> = callbackFlow {
        try {
            trySend(
                Resource.Success(
                    animeSource.getTopAnime(1).data.take(takeTopTeen).animeUI()
                )
            )
        } catch (e: HttpException) {
            trySend(Resource.Error(e.message.toString()))
        } catch (e: Exception) {
            trySend(Resource.Error(e.message.toString()))
        }
        awaitClose { channel.close() }
    }

    override fun getRecommendations(): Flow<Resource<List<RecommendationsUI>>> = callbackFlow {
        try {
            val data = animeSource.getRecommendations(1).data.take(takeTopTeen)
            trySend(
                Resource.Success(data.map { it.entry.recommendationsUI() }.flatten())
            )
        } catch (e: HttpException) {
            trySend(Resource.Error(e.message.toString()))
        } catch (e: Exception) {
            trySend(Resource.Error(e.message.toString()))
        }
        awaitClose { channel.close() }
    }

    override fun getSeasonNow(): Flow<Resource<List<SeasonNowUI>>> = callbackFlow {
        try {
            trySend(
                Resource.Success(
                    animeSource.getSeasonNow(1).data.take(takeTopTeen).seasonNowUI()
                )
            )
        } catch (e: HttpException) {
            trySend(Resource.Error(e.message.toString()))
        } catch (e: Exception) {
            trySend(Resource.Error(e.message.toString()))
        }
        awaitClose { channel.close() }
    }

    override fun getEpisodePopular(): Flow<Resource<List<EpisodePopularUI>>> = callbackFlow {
        try {
            trySend(
                Resource.Success(
                    animeSource.getEpisodePopular(1).data.take(takeTopTeen).episodePopularUI()
                )
            )
        } catch (e: HttpException) {
            trySend(Resource.Error(e.message.toString()))
        } catch (e: Exception) {
            trySend(Resource.Error(e.message.toString()))
        }
        awaitClose { channel.close() }
    }

    companion object {
        private const val takeTopTeen = 10
    }
}