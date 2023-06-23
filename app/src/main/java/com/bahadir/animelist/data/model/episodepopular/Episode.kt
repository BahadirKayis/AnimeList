package com.bahadir.animelist.data.model.episodepopular

import com.google.gson.annotations.SerializedName

data class Episode(
    @SerializedName("mal_id")
    val malId: Int,
    val premium: Boolean,
    val title: String,
    val url: String
)