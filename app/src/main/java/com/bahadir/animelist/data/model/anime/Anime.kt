package com.bahadir.animelist.data.model.anime

import com.bahadir.animelist.data.model.common.Pagination

data class Anime(
    val data: List<AnimeData>,
    val pagination: Pagination
)