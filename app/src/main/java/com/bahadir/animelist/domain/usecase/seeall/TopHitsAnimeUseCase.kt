package com.bahadir.animelist.domain.usecase.seeall

import com.bahadir.animelist.domain.repository.PagingRepository
import javax.inject.Inject

class TopHitsAnimeUseCase @Inject constructor(private val pagingRepo: PagingRepository) {
    operator fun invoke() = pagingRepo.getTopAnime()

}