package com.bahadir.animelist.domain.model.home


data class AnimeUI(
    val id: Int,
    val url: String,
    val score: Double,
    val rank: Int,
    val imgUrl: String,
    val year: Int,
    val title: String,
    val genres: String,
)