package com.bahadir.animelist.data.source.remote

import com.bahadir.animelist.common.Constants.ANIME_COMMENTS
import com.bahadir.animelist.common.Constants.ANIME_FULL
import com.bahadir.animelist.common.Constants.ANIME_PHOTOS
import com.bahadir.animelist.common.Constants.ANIME_RECOMMENDATIONS
import com.bahadir.animelist.common.Constants.RECENT_EPISODES
import com.bahadir.animelist.common.Constants.RECOMMENDATIONS_ANIM
import com.bahadir.animelist.common.Constants.SCHEDULE
import com.bahadir.animelist.common.Constants.SEARCH
import com.bahadir.animelist.common.Constants.SEASON_NOW
import com.bahadir.animelist.common.Constants.TOP_ANIME
import com.bahadir.animelist.data.model.anime.Anime
import com.bahadir.animelist.data.model.animedetail.AnimeDetailNetwork
import com.bahadir.animelist.data.model.animedetail.AnimePhotosNetwork
import com.bahadir.animelist.data.model.animedetail.AnimeRecommendationsNetwork
import com.bahadir.animelist.data.model.comments.CommentsNetwork
import com.bahadir.animelist.data.model.episodepopular.EpisodePopular
import com.bahadir.animelist.data.model.recommendations.Recommendations
import com.bahadir.animelist.data.model.schedule.ScheduleNetwork
import com.bahadir.animelist.data.model.seasonnow.SeasonNow
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AnimeService {
    @GET(TOP_ANIME)
    suspend fun getTopAnime(@Query("page") page: Int): Anime

    @GET(RECOMMENDATIONS_ANIM)
    suspend fun getRecommendations(@Query("page") page: Int): Recommendations

    @GET(SEASON_NOW)
    suspend fun getSeasonNow(@Query("page") page: Int): SeasonNow

    @GET(RECENT_EPISODES)
    suspend fun getEpisodePopular(@Query("page") limit: Int): EpisodePopular

    @GET(ANIME_FULL)
    suspend fun getAnime(@Path("id") id: Int): AnimeDetailNetwork

    @GET(ANIME_RECOMMENDATIONS)
    suspend fun getAnimeRecommendations(@Path("id") id: Int): AnimeRecommendationsNetwork

    @GET(ANIME_PHOTOS)
    suspend fun getAnimePhotos(@Path("id") id: Int): AnimePhotosNetwork

    @GET(ANIME_COMMENTS)
    suspend fun getAnimeComments(@Path("id") id: Int): CommentsNetwork

    @GET(SEARCH)
    suspend fun getSearch(
        @Query("page") page: Int,
        @Query("q") query: String
    ): Anime

    @GET(SCHEDULE)
    suspend fun getSchedule(
        @Query("page") page: Int,
        @Query("filter") day: String
    ): ScheduleNetwork
}