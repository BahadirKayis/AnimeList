package com.bahadir.animelist.data.model.common

import com.google.gson.annotations.SerializedName

data class Demographic(
    @SerializedName("mal_id")
    val malId: Int,
    val name: String,
    val type: String,
    val url: String
)