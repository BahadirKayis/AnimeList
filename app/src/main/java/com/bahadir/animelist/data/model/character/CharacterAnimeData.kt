package com.bahadir.animelist.data.model.character

import com.google.gson.annotations.SerializedName

data class CharacterAnimeData(
    @SerializedName("anime")
    val characterAnime: Anime,
    @SerializedName("role")
    val role: String
)