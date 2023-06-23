package com.bahadir.animelist.data.model.character

import com.bahadir.animelist.data.model.common.Images
import com.google.gson.annotations.SerializedName


data class CharacterData(
    val about: String,
    val favorites: Int,
    val images: Images,
    @SerializedName("mal_id")
    val malId: Int,
    val name: String,
    @SerializedName("name_kanji")
    val nameKanji: String,
    val nicknames: List<String>,
    val url: String
)

data class CharacterDataNetwork(val data: CharacterData)