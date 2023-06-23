package com.bahadir.animelist.data.model.common

import com.google.gson.annotations.SerializedName

data class Trailer(
    @SerializedName("embed_url")
    val embedUrl: String,
    val images: ImagesX,
    val url: String,
    @SerializedName("youtube_id")
    val youtubeId: String?
)