package com.bahadir.animelist.domain.mapper

import android.content.Context
import com.bahadir.animelist.common.extensions.getGenres
import com.bahadir.animelist.common.extensions.timeSinceDate
import com.bahadir.animelist.data.model.animedetail.AnimeDetailData
import com.bahadir.animelist.data.model.comments.CommentsData
import com.bahadir.animelist.domain.model.AnimeDetailUI
import com.bahadir.animelist.domain.model.detail.CommentsUI
import com.bahadir.animelist.domain.model.home.AnimeUI

fun List<com.bahadir.animelist.data.model.anime.AnimeData>.animeUI() = map {
    AnimeUI(
        id = it.malId,
        url = it.url,
        score = it.score,
        rank = it.rank,
        imgUrl = it.images.webp.largeImageUrl ?: it.images.webp.imageUrl,
        genres = it.genres.getGenres(),
        title = it.title,
        year = it.year,
    )
}

fun com.bahadir.animelist.data.model.character.Anime.characterAnimeUI() = run {
    AnimeUI(
        id = malId,
        url = url,
        score = 0.0,
        rank = 0,
        imgUrl = images.webp.largeImageUrl ?: images.webp.imageUrl,
        genres = "",
        title = "",
        year = 0,
    )
}

fun AnimeDetailData.animeDetailUI() =
    AnimeDetailUI(
        id = malId,
        title = title,
        year = year.toString(),
        type = type,
        score = score.toString(),
        synopsis = synopsis,
        videoUrl = trailer.youtubeId,
        genres = genres.getGenres(),
        url = url
    )

fun List<CommentsData>.commentsUI(context: Context) = map {
    CommentsUI(
        date = it.date.timeSinceDate(context),
        comment = it.review,
        isSpoiler = it.isSpoiler,
        loveIt = it.reactions.loveIt,
        userName = it.user.username,
        imageUrl = it.user.images.webp.imageUrl

    )
}