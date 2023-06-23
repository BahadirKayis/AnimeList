package com.bahadir.animelist.common

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
enum class SeeAllAnime : Parcelable {
    TOP_ANIME,
    SEASON_NOW,
    RECOMMENDATION
}