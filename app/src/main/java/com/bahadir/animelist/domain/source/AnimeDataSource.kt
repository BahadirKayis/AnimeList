package com.bahadir.animelist.domain.source

import com.bahadir.animelist.data.model.anime.Anime
import com.bahadir.animelist.data.model.animedetail.AnimeDetailNetwork
import com.bahadir.animelist.data.model.animedetail.AnimePhotosNetwork
import com.bahadir.animelist.data.model.animedetail.AnimeRecommendationsNetwork
import com.bahadir.animelist.data.model.comments.CommentsNetwork
import com.bahadir.animelist.data.model.episodepopular.EpisodePopular
import com.bahadir.animelist.data.model.recommendations.Recommendations
import com.bahadir.animelist.data.model.schedule.ScheduleNetwork
import com.bahadir.animelist.data.model.seasonnow.SeasonNow

interface AnimeDataSource {
    suspend fun getTopAnime(page: Int): Anime
    suspend fun getRecommendations(page: Int): Recommendations
    suspend fun getSeasonNow(page: Int): SeasonNow
    suspend fun getEpisodePopular(page: Int): EpisodePopular
    suspend fun getAnime(id: Int): AnimeDetailNetwork
    suspend fun getAnimeRecommendations(id: Int): AnimeRecommendationsNetwork
    suspend fun getAnimePhotos(id: Int): AnimePhotosNetwork
    suspend fun getAnimeComments(id: Int): CommentsNetwork
    suspend fun getSearch(
        page: Int,
        query: String
    ): Anime

    suspend fun getSchedule(page: Int, filterDay: String): ScheduleNetwork

}