package com.bahadir.animelist.domain.model.home

data class SeasonNowUI(
    val id: Int,
    val url: String,
    val score: Double,
    val rank: Int,
    val imgUrl: String,
    val title: String,
    val trailer: String?,
    val year: Int,
    val season: String,
    val genres: String
)