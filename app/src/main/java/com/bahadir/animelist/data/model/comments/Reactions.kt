package com.bahadir.animelist.data.model.comments

import com.google.gson.annotations.SerializedName

data class Reactions(
    val confusing: Int,
    val creative: Int,
    val funny: Int,
    val informative: Int,
    @SerializedName("love_it")
    val loveIt: Int,
    val nice: Int,
    val overall: Int,
    @SerializedName("well_written")
    val wellWritten: Int
)