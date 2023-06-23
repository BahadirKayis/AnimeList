package com.bahadir.animelist.data.model.common

import com.google.gson.annotations.SerializedName

data class Entry(
    val images: Images,
    @SerializedName("mal_id")
    val malId: Int,
    val title: String,
    val url: String
)