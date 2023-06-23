package com.bahadir.animelist.data.model.common

data class Broadcast(
    val day: String,
    val string: String,
    val time: String? = null,
    val timezone: String
)