package com.bahadir.animelist.data.model.schedule

import com.bahadir.animelist.data.model.common.*
import com.google.gson.annotations.SerializedName

data class ScheduleData(
    val aired: Aired,
    val airing: Boolean,
    val approved: Boolean,
    val background: String,
    val broadcast: Broadcast,
    val demographics: List<Demographic>,
    val duration: String,
    val episodes: Int,
    @SerializedName("explicit_genres")
    val explicitGenres: List<Any>,
    val favorites: Int,
    val genres: List<Genre>,
    val images: Images,
    val licensors: List<Licensor>,
    @SerializedName("mal_id")
    val malId: Int,
    val members: Int,
    val popularity: Int,
    val producers: List<Producer>,
    val rank: Int,
    val rating: String,
    val score: Double,
    @SerializedName("scored_by")
    val scoredBy: Int,
    val season: String,
    val source: String,
    val status: String,
    val studios: List<Studio>,
    val synopsis: String,
    val themes: List<Theme>,
    val title: String,
    @SerializedName("title_english")
    val titleEnglish: String,
    @SerializedName("title_japanese")
    val titleJapanese: String,
    @SerializedName("titleSynonyms")
    val titleSynonyms: List<String>,
    val titles: List<Title>,
    val trailer: Trailer,
    val type: String,
    val url: String,
    val year: Int
)