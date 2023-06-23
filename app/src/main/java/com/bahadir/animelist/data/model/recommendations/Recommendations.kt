package com.bahadir.animelist.data.model.recommendations

import com.bahadir.animelist.data.model.common.Pagination

data class Recommendations(
    val data: List<RecommendationData>,
    val pagination: Pagination
)