package com.bahadir.animelist.data.model.character

import com.bahadir.animelist.data.model.common.Pagination

data class Characters(
    val data: List<CharacterData>,
    val pagination: Pagination
)