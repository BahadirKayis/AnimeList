package com.bahadir.animelist.domain.model.home

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CharactersUI(
    val id: Int,
    val name: String,
    val url: String,
    val imageUrl: String,
    val nameKanji: String,
    val nicknames: List<String>,
    val about: String,
    val favorites: Int,
) : Parcelable


