package com.bahadir.animelist.domain.usecase.animedetail

import com.bahadir.animelist.domain.usecase.character.GetCharacterUseCase
import javax.inject.Inject

data class AnimeDetailUseCase @Inject constructor(
    val getAnimePhoto: AnimeDetailAnimPhotoUseCase,
    val getCharactersAnimeDetail: AnimeDetailCharacters,
    val getOtherInformation: AnimeDetailCommentsMoreLikeUseCase,
    val getCharacter: GetCharacterUseCase
)