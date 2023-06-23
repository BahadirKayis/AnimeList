package com.bahadir.animelist.domain.usecase

import androidx.paging.PagingData
import com.bahadir.animelist.domain.model.home.AnimeUI
import com.bahadir.animelist.domain.repository.PagingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchUseCase @Inject constructor(private val pagingRepo: PagingRepository) {
    operator fun invoke(query: String): Flow<PagingData<AnimeUI>> {
//        val type = if (filter.type == FilterType.ALL) "" else filter.type.toString().lowercase()
//        val status =
//            if (filter.status == FilterStatus.ALL) "" else filter.status.toString().lowercase()
//        val genres = filter.genres.pars()
        return pagingRepo.getSearchAnime(query)
    }
}

