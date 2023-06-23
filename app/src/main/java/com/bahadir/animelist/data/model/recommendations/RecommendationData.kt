package com.bahadir.animelist.data.model.recommendations

import com.bahadir.animelist.data.model.common.Entry
import com.google.gson.annotations.SerializedName

data class RecommendationData(
    val content: String,
    val date: String,
    val entry: List<Entry>,
    @SerializedName("mal_id")
    val malId: String,
    val user: User
)