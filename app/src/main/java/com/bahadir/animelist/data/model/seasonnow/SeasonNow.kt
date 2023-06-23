package com.bahadir.animelist.data.model.seasonnow

import com.bahadir.animelist.data.model.common.Pagination

data class SeasonNow(
    val data: List<SeasonData>,
    val pagination: Pagination
)