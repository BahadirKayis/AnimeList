package com.bahadir.animelist.presentation.schedule


import androidx.paging.PagingData
import com.bahadir.animelist.domain.model.ScheduleUI
import com.bahadir.animelist.presentation.base.ees.State

data class ScheduleUIState(
    val isLoading: Boolean = false,
    val anime: PagingData<ScheduleUI>? = null,
) : State