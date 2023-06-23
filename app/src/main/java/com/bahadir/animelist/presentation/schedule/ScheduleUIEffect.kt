package com.bahadir.animelist.presentation.schedule

import com.bahadir.animelist.presentation.base.ees.Effect

sealed class ScheduleUIEffect : Effect {
    data class SnackBarMessage(val message: String) : ScheduleUIEffect()
    data class ActionPlayVideo(val url: String) : ScheduleUIEffect()

}