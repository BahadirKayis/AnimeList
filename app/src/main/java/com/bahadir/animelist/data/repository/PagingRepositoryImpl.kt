package com.bahadir.animelist.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.bahadir.animelist.domain.model.ScheduleUI
import com.bahadir.animelist.domain.model.home.AnimeUI
import com.bahadir.animelist.domain.model.home.CharactersUI
import com.bahadir.animelist.domain.model.home.RecommendationsUI
import com.bahadir.animelist.domain.model.home.SeasonNowUI
import com.bahadir.animelist.domain.pagging.PagingCharacters
import com.bahadir.animelist.domain.pagging.PagingRecommendation
import com.bahadir.animelist.domain.pagging.PagingSchedules
import com.bahadir.animelist.domain.pagging.PagingSearch
import com.bahadir.animelist.domain.pagging.PagingSeasonNow
import com.bahadir.animelist.domain.pagging.PagingTopAnime
import com.bahadir.animelist.domain.repository.PagingRepository
import com.bahadir.animelist.domain.source.AnimeDataSource
import com.bahadir.animelist.domain.source.CharacterDataSource
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class PagingRepositoryImpl(
    private val animeSource: AnimeDataSource, private val characterSource: CharacterDataSource
) : PagingRepository {
    override fun getTopAnime(): Flow<PagingData<AnimeUI>> = callbackFlow {
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE, enablePlaceholders = false, maxSize = 200
            )
        ) {
            PagingTopAnime(animeSource)
        }.flow.collect {
            trySend(it)
        }
        awaitClose { channel.close() }
    }

    override fun getSeasonNow(): Flow<PagingData<SeasonNowUI>> = callbackFlow {
        Pager(config = PagingConfig(
            pageSize = PAGE_SIZE, enablePlaceholders = false,
        ), pagingSourceFactory = {
            PagingSeasonNow(animeSource)
        }).flow.collect {
            trySend(it)
        }
        awaitClose { channel.close() }
    }

    override fun getRecommendation(): Flow<PagingData<RecommendationsUI>> = callbackFlow {
        Pager(config = PagingConfig(
            pageSize = PAGE_SIZE, enablePlaceholders = false
        ), pagingSourceFactory = {
            PagingRecommendation(animeSource)
        }).flow.collect {
            trySend(it)
        }
        awaitClose { channel.close() }
    }


    override fun getCharacters(): Flow<PagingData<CharactersUI>> = callbackFlow {
        Pager(config = PagingConfig(
            pageSize = PAGE_SIZE, enablePlaceholders = false
        ), pagingSourceFactory = {
            PagingCharacters(characterSource)
        }).flow.collect {
            trySend(it)
        }
        awaitClose { channel.close() }
    }

    override fun getSearchAnime(query: String): Flow<PagingData<AnimeUI>> = callbackFlow {
        Pager(config = PagingConfig(
            pageSize = PAGE_SIZE, enablePlaceholders = false
        ), pagingSourceFactory = {
            PagingSearch(animeSource, query)
        }).flow.collect {
            trySend(it)
        }
        awaitClose { channel.close() }
    }

    override fun getSchedules(filterDay: String): Flow<PagingData<ScheduleUI>> =
        callbackFlow {
            Pager(config = PagingConfig(
                pageSize = PAGE_SIZE, enablePlaceholders = false
            ), pagingSourceFactory = {
                PagingSchedules(animeSource, filterDay)
            }).flow.collect {
                trySend(it)
            }
            awaitClose { channel.close() }
        }

    companion object {
        private const val PAGE_SIZE = 10
    }
}