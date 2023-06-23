package com.bahadir.animelist.domain.model


data class AnimeDetailUI(
    val id: Int,
    val title: String,
    val year: String,
    val type: String,
    val score: String,
    val synopsis: String,
    val videoUrl: String? = null,
    val genres: String,
    val url: String
)