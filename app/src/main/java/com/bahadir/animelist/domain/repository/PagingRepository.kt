package com.bahadir.animelist.domain.repository

import androidx.paging.PagingData
import com.bahadir.animelist.domain.model.ScheduleUI
import com.bahadir.animelist.domain.model.home.AnimeUI
import com.bahadir.animelist.domain.model.home.CharactersUI
import com.bahadir.animelist.domain.model.home.RecommendationsUI
import com.bahadir.animelist.domain.model.home.SeasonNowUI
import kotlinx.coroutines.flow.Flow

interface PagingRepository {
    fun getTopAnime(): Flow<PagingData<AnimeUI>>
    fun getSeasonNow(): Flow<PagingData<SeasonNowUI>>
    fun getRecommendation(): Flow<PagingData<RecommendationsUI>>
    fun getCharacters(): Flow<PagingData<CharactersUI>>
    fun getSearchAnime(query: String): Flow<PagingData<AnimeUI>>
    fun getSchedules(filterDay: String): Flow<PagingData<ScheduleUI>>

}