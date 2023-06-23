package com.bahadir.animelist.data.model.animeCharacter

import com.bahadir.animelist.data.model.common.Images
import com.google.gson.annotations.SerializedName

data class Character(
    val images: Images,
    @SerializedName("mal_id")
    val malId: Int,
    val name: String,
    val url: String
)