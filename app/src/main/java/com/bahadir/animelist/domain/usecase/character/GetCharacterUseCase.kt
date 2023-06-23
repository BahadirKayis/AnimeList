package com.bahadir.animelist.domain.usecase.character

import com.bahadir.animelist.domain.repository.CharacterRepository
import javax.inject.Inject

class GetCharacterUseCase @Inject constructor(val repository: CharacterRepository) {
    operator fun invoke(id: Int) = repository.getCharacter(id)
}