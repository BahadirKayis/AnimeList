package com.bahadir.animelist.data.model.comments

import com.bahadir.animelist.data.model.common.Images

data class User(
    val images: Images,
    val url: String,
    val username: String
)