package com.bahadir.animelist.domain.usecase.animedetail

import com.afdhal_fa.imageslider.model.SlideUIModel
import com.bahadir.animelist.common.Resource
import com.bahadir.animelist.domain.model.AnimeDetailUI
import com.bahadir.animelist.domain.repository.AnimeRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class AnimeDetailAnimPhotoUseCase @Inject constructor(
    private val animeRepo: AnimeRepository,
) {
    operator fun invoke(id: Int): Flow<Resource<Pair<AnimeDetailUI, List<SlideUIModel>>>> =
        callbackFlow {
            val anime = animeRepo.getAnime(id)
            val animePictures = animeRepo.getAnimePhotos(id)
            combine(
                anime, animePictures
            ) { anim, pictures ->
                when {
                    anim is Resource.Success && pictures is Resource.Success -> {
                        trySend(Resource.Success(Pair(anim.data, pictures.data)))

                    }

                    anim is Resource.Error -> {
                        trySend(Resource.Error("Anime Error: " + anim.message))
                    }

                    pictures is Resource.Error -> {
                        trySend(Resource.Error("Pictures Error: " + pictures.message))
                    }

                    else -> {}
                }
            }.collect()
            awaitClose { channel.close() }
        }
}
