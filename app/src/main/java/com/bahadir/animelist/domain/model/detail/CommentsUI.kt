package com.bahadir.animelist.domain.model.detail

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CommentsUI(
    val date: String,
    val comment: String,
    val isSpoiler: Boolean,
    val loveIt: Int,
    val userName: String,
    val imageUrl: String
) : Parcelable
