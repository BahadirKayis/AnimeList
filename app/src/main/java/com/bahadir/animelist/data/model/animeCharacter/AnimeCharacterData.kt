package com.bahadir.animelist.data.model.animeCharacter

import com.google.gson.annotations.SerializedName

data class AnimeCharacterData(
    @SerializedName("character")
    val character: Character,
    val favorites: Int,
    val role: String,
)