package com.bahadir.animelist.data.repository.local

import com.bahadir.animelist.data.model.schedule.ScheduleDay

object LocalData {
    fun getScheduleFilterDay(): List<ScheduleDay> {
        return listOf(
            ScheduleDay(1, "Monday"),
            ScheduleDay(2, "Tuesday"),
            ScheduleDay(3, "Wednesday"),
            ScheduleDay(4, "Thursday"),
            ScheduleDay(5, "Friday"),
            ScheduleDay(6, "Unknown"),
            ScheduleDay(7, "Other"),
        )
    }

}