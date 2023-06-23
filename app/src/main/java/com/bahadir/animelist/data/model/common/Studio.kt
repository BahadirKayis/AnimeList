package com.bahadir.animelist.data.model.common

import com.google.gson.annotations.SerializedName

data class Studio(
    @SerializedName("mal_id")
    val malId: Int,
    val name: String,
    val type: String,
    val url: String
)