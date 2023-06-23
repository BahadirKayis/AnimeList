package com.bahadir.animelist.data.model.animedetail

import com.bahadir.animelist.data.model.common.Entry

data class RecommendationAnimeDetailData(
    val entry: Entry,
    val url: String,
    val votes: Int
)