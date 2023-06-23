package com.bahadir.animelist.presentation.schedule

import com.bahadir.animelist.presentation.base.ees.Event

sealed class ScheduleUIEvent : Event {
    data class GetScheduleAnime(val day: String) : ScheduleUIEvent()
    data class ActionPlayVideo(val url: String) : ScheduleUIEvent()
}