package com.bahadir.animelist.data.model.character

import com.bahadir.animelist.data.model.common.Images
import com.google.gson.annotations.SerializedName

data class Anime(
    val images: Images,
    @SerializedName("mal_id")
    val malId: Int,
    val title: String,
    val url: String
)