package com.bahadir.animelist.domain.model

data class ScheduleUI(
    val trailer: String? = null,
    val trailerImg: String? = null,
    val time: String? = null,
    val imgUrl: String,
    val title: String,
    val episode: Int,
)
