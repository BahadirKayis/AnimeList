package com.bahadir.animelist.domain.pagging

import com.bahadir.animelist.domain.mapper.scheduleUI
import com.bahadir.animelist.domain.model.ScheduleUI
import com.bahadir.animelist.domain.source.AnimeDataSource

class PagingSchedules(
    private val animeSource: AnimeDataSource, private val query: String
) : BasePaging<ScheduleUI>() {
    override suspend fun fetchData(page: Int): List<ScheduleUI> {
        val response = animeSource.getSchedule(page, query).data
        return response.scheduleUI().sortedBy { it.time ?: "zzz" }
    }
}