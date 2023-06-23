package com.bahadir.animelist.domain.repository

import com.afdhal_fa.imageslider.model.SlideUIModel
import com.bahadir.animelist.common.Resource
import com.bahadir.animelist.domain.model.AnimeDetailUI
import com.bahadir.animelist.domain.model.detail.CommentsUI
import com.bahadir.animelist.domain.model.home.AnimeUI
import com.bahadir.animelist.domain.model.home.EpisodePopularUI
import com.bahadir.animelist.domain.model.home.RecommendationsUI
import com.bahadir.animelist.domain.model.home.SeasonNowUI
import kotlinx.coroutines.flow.Flow

interface AnimeRepository {
    fun getTopAnime(): Flow<Resource<List<AnimeUI>>>
    fun getRecommendations(): Flow<Resource<List<RecommendationsUI>>>
    fun getSeasonNow(): Flow<Resource<List<SeasonNowUI>>>
    fun getEpisodePopular(): Flow<Resource<List<EpisodePopularUI>>>
    fun getAnime(id: Int): Flow<Resource<AnimeDetailUI>>
    fun getAnimeRecommendations(id: Int): Flow<Resource<List<RecommendationsUI>>>
    fun getAnimePhotos(id: Int): Flow<Resource<List<SlideUIModel>>>
    fun getAnimeComments(id: Int): Flow<Resource<List<CommentsUI>>>

}