package com.bahadir.animelist.domain.usecase.animedetail

import com.bahadir.animelist.domain.repository.CharacterRepository
import javax.inject.Inject

class AnimeDetailCharacters @Inject constructor(private val characterRepo: CharacterRepository) {
    operator fun invoke(id: Int) = characterRepo.getAnimeCharacter(id)

}