package com.bahadir.animelist.domain.mapper

import com.bahadir.animelist.common.extensions.convertLocalTimeZone
import com.bahadir.animelist.common.extensions.getGenres
import com.bahadir.animelist.data.model.animeCharacter.AnimeCharacterData
import com.bahadir.animelist.data.model.character.CharacterData
import com.bahadir.animelist.data.model.common.Entry
import com.bahadir.animelist.data.model.episodepopular.EpisodeData
import com.bahadir.animelist.data.model.schedule.ScheduleData
import com.bahadir.animelist.data.model.seasonnow.SeasonData
import com.bahadir.animelist.domain.model.ScheduleUI
import com.bahadir.animelist.domain.model.detail.AnimeCharacterUI
import com.bahadir.animelist.domain.model.home.CharactersUI
import com.bahadir.animelist.domain.model.home.EpisodePopularUI
import com.bahadir.animelist.domain.model.home.RecommendationsUI
import com.bahadir.animelist.domain.model.home.SeasonNowUI


fun List<CharacterData>.topCharactersUI() = map { data ->
    CharactersUI(
        id = data.malId,
        name = data.name,
        url = data.url,
        imageUrl = data.images.webp.largeImageUrl ?: data.images.webp.imageUrl,
        about = data.about,
        favorites = data.favorites,
        nameKanji = data.nameKanji,
        nicknames = data.nicknames
    )
}

fun CharacterData.charactersUI() =
    CharactersUI(
        id = malId,
        name = name,
        url = url,
        imageUrl = images.webp.largeImageUrl ?: images.webp.imageUrl,
        about = about,
        favorites = favorites,
        nameKanji = nameKanji,
        nicknames = nicknames
    )


fun List<Entry>.recommendationsUI() = map {
    RecommendationsUI(
        id = it.malId,
        title = it.title,
        imgUrl = it.images.webp.largeImageUrl ?: it.images.webp.imageUrl,
    )
}

fun Entry.recommendationsUI() = run {
    RecommendationsUI(
        id = malId,
        title = title,
        imgUrl = images.webp.largeImageUrl ?: images.webp.imageUrl,

        )
}

fun List<SeasonData>.seasonNowUI() = map {
    SeasonNowUI(
        id = it.malId,
        url = it.url,
        score = it.score,
        rank = it.rank,
        imgUrl = it.images.webp.largeImageUrl ?: it.images.webp.imageUrl,
        title = it.title,
        trailer = it.trailer.youtubeId,
        year = it.year,
        season = it.season ?: "",
        genres = it.genres.getGenres()
    )
}

fun List<EpisodeData>.episodePopularUI() = map {
    EpisodePopularUI(
        id = it.entry.malId,
        imgUrl = it.entry.images.webp.largeImageUrl ?: it.entry.images.webp.imageUrl,
    )
}

fun List<AnimeCharacterData>.animeCharacterUI() = map {
    AnimeCharacterUI(
        malId = it.character.malId,
        name = it.character.name,
        role = it.role,
        imageUrl = it.character.images.webp.largeImageUrl ?: it.character.images.webp.imageUrl,
    )
}


fun List<ScheduleData>.scheduleUI() = map {
    ScheduleUI(
        trailer = it.trailer.youtubeId,
        trailerImg = it.trailer.images.maximumImageUrl,
        time = it.broadcast.time?.convertLocalTimeZone(it.broadcast.timezone),
        imgUrl = it.images.webp.largeImageUrl ?: it.images.webp.imageUrl,
        title = it.title,
        episode = it.episodes,

    )
}



