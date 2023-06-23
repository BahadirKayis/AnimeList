package com.bahadir.animelist.domain.usecase

import androidx.paging.PagingData
import com.bahadir.animelist.domain.model.ScheduleUI
import com.bahadir.animelist.domain.repository.PagingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetScheduleAnimeUseCase @Inject constructor(private val repo: PagingRepository) {
    operator fun invoke(day: String): Flow<PagingData<ScheduleUI>> = repo.getSchedules(day)

}