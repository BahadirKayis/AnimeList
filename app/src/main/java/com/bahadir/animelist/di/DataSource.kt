package com.bahadir.animelist.di

import com.bahadir.animelist.data.source.remote.AnimeDataSourceImpl
import com.bahadir.animelist.data.source.remote.AnimeService
import com.bahadir.animelist.data.source.remote.CharacterDataSourceImpl
import com.bahadir.animelist.data.source.remote.CharacterService
import com.bahadir.animelist.domain.source.AnimeDataSource
import com.bahadir.animelist.domain.source.CharacterDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataSource {

    @Provides
    @Singleton
    fun provideRemoteAnimeDataSource(animeService: AnimeService): AnimeDataSource =
        AnimeDataSourceImpl(animeService)

    @Provides
    @Singleton
    fun provideRemoteCharacterDataSource(characterService: CharacterService): CharacterDataSource =
        CharacterDataSourceImpl(characterService)
}