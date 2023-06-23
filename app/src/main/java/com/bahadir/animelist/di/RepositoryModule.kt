package com.bahadir.animelist.di

import android.content.Context
import com.bahadir.animelist.data.repository.AnimeRepositoryImpl
import com.bahadir.animelist.data.repository.CharacterRepositoryImpl
import com.bahadir.animelist.data.repository.PagingRepositoryImpl
import com.bahadir.animelist.domain.repository.AnimeRepository
import com.bahadir.animelist.domain.repository.CharacterRepository
import com.bahadir.animelist.domain.repository.PagingRepository
import com.bahadir.animelist.domain.source.AnimeDataSource
import com.bahadir.animelist.domain.source.CharacterDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun providerAnimeRepository(
        animeService: AnimeDataSource,
        @ApplicationContext context: Context
    ): AnimeRepository =
        AnimeRepositoryImpl(animeService, context)

    @Provides
    @Singleton
    fun providerCharacterRepository(characterService: CharacterDataSource): CharacterRepository =
        CharacterRepositoryImpl(characterService)

    @Provides
    @Singleton
    fun providerPagingRepository(
        characterService: CharacterDataSource,
        animeService: AnimeDataSource
    ): PagingRepository =
        PagingRepositoryImpl(animeService, characterService)

}