package com.bahadir.animelist.data.model.episodepopular

import com.bahadir.animelist.data.model.common.Pagination

data class EpisodePopular(
    val data: List<EpisodeData>,
    val pagination: Pagination
)