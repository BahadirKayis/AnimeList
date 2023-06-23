package com.bahadir.animelist.data.source.remote

import com.bahadir.animelist.data.model.anime.Anime
import com.bahadir.animelist.data.model.animedetail.AnimeDetailNetwork
import com.bahadir.animelist.data.model.animedetail.AnimePhotosNetwork
import com.bahadir.animelist.data.model.animedetail.AnimeRecommendationsNetwork
import com.bahadir.animelist.data.model.comments.CommentsNetwork
import com.bahadir.animelist.data.model.episodepopular.EpisodePopular
import com.bahadir.animelist.data.model.recommendations.Recommendations
import com.bahadir.animelist.data.model.schedule.ScheduleNetwork
import com.bahadir.animelist.data.model.seasonnow.SeasonNow
import com.bahadir.animelist.domain.source.AnimeDataSource

class AnimeDataSourceImpl(
    private val animeService: AnimeService
) : AnimeDataSource {
    override suspend fun getTopAnime(page: Int): Anime = animeService.getTopAnime(page)
    override suspend fun getRecommendations(page: Int): Recommendations =
        animeService.getRecommendations(page)

    override suspend fun getSeasonNow(page: Int): SeasonNow = animeService.getSeasonNow(page)
    override suspend fun getEpisodePopular(page: Int): EpisodePopular =
        animeService.getEpisodePopular(page)

    override suspend fun getAnime(id: Int): AnimeDetailNetwork = animeService.getAnime(id)
    override suspend fun getAnimeRecommendations(id: Int): AnimeRecommendationsNetwork =
        animeService.getAnimeRecommendations(id)

    override suspend fun getAnimePhotos(id: Int): AnimePhotosNetwork =
        animeService.getAnimePhotos(id)

    override suspend fun getAnimeComments(id: Int): CommentsNetwork =
        animeService.getAnimeComments(id)

    override suspend fun getSearch(
        page: Int,
        query: String
    ): Anime =
        animeService.getSearch(page = page, query)

    override suspend fun getSchedule(page: Int, filterDay: String): ScheduleNetwork =
        animeService.getSchedule(page, filterDay)
}