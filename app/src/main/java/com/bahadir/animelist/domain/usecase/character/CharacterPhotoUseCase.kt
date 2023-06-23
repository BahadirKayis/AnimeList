package com.bahadir.animelist.domain.usecase.character

import com.afdhal_fa.imageslider.model.SlideUIModel
import com.bahadir.animelist.common.Resource
import com.bahadir.animelist.domain.model.home.AnimeUI
import com.bahadir.animelist.domain.repository.CharacterRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class CharacterPhotoUseCase @Inject constructor(
    private val characterRepo: CharacterRepository,
) {
    operator fun invoke(id: Int): Flow<Resource<Pair<List<AnimeUI>, List<SlideUIModel>>>> =
        callbackFlow {
            try {
                val anime = characterRepo.getCharacterAnime(id)
                val photos = characterRepo.getCharacterPhotos(id)

                combine(anime, photos) { a, p ->
                    when {
                        a is Resource.Success && p is Resource.Success -> {

                            val slideList: List<SlideUIModel> =
                                p.data.map { SlideUIModel(it, "") }

                            trySend(Resource.Success(Pair(a.data, slideList)))
                        }

                        a is Resource.Error -> {
                            trySend(Resource.Error(a.message))
                        }

                        p is Resource.Error -> {
                            trySend(Resource.Error(p.message))
                        }

                        else -> {}
                    }
                }.collect()

            } catch (e: Exception) {
                trySend(Resource.Error(e.message.toString()))
            }
            awaitClose { channel.close() }
        }

}