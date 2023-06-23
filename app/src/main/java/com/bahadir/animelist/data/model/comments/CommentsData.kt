package com.bahadir.animelist.data.model.comments

import com.google.gson.annotations.SerializedName

data class CommentsData(
    val date: String,
    @SerializedName("episodes_watched")
    val episodesWatched: Any,
    @SerializedName("isPreliminary")
    val isPreliminary: Boolean,
    @SerializedName("is_spoiler")
    val isSpoiler: Boolean,
    @SerializedName("mal_id")
    val malId: Int,
    val reactions: Reactions,
    val review: String,
    val score: Int,
    val tags: List<String>,
    val type: String,
    val url: String,
    val user: User
)