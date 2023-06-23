package com.bahadir.animelist.data.model.animedetail

import com.bahadir.animelist.data.model.common.Entry

data class Relation(
    val entry: List<Entry>,
    val relation: String
)