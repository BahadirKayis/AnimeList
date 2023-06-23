package com.bahadir.animelist.domain.model.home

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecommendationsUI(
    val id: Int,
    val title: String,
    val imgUrl: String,
) : Parcelable
