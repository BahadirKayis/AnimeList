package com.bahadir.animelist.common.extensions

import android.content.Context
import android.os.Build
import android.util.Patterns
import com.bahadir.animelist.R
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone
import java.util.regex.Pattern


fun String.isValidPassword(): Boolean {
    val passwordREGEX = Pattern.compile(
        "^" + "(?=.*[a-z])" +         //at least 1 lower case letter
                "(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                ".{4,}" +               //at least 4 characters
                "$"
    )
    return passwordREGEX.matcher(this).matches()
}

fun String.isValidEmail(): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.timeSinceDate(context: Context): String {
    val hour = 60
    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault())
    val date = format.parse(this)!!

    val currentDate = Calendar.getInstance().time
    val seconds = (currentDate.time - date.time) / 1000
    return if (seconds < hour) {
        "şimdi"
    } else if (seconds < hour * hour) {
        val minutes = seconds / hour
        context.resources.getString(R.string.hours_ago, minutes)
    } else if (seconds < hour * hour * 24) {
        val hours = seconds / (hour * hour)
        context.resources.getString(R.string.hours_ago, hours)
    } else {
        val days = seconds / (hour * hour * 24)
        context.resources.getString(R.string.days_ago, days)
    }
}

fun String.convertLocalTimeZone(timezone: String): String {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val dateTime = LocalTime.parse(
            this,
            DateTimeFormatter.ofPattern(
                "HH:mm", Locale.getDefault()
            )
        )
        val zonedDateTime = dateTime.atDate(LocalDate.now()).atZone(ZoneId.of(timezone))
        val localDateTime = zonedDateTime
            .withZoneSameInstant(ZoneId.systemDefault())
            .toLocalDateTime()
        return localDateTime.toString().substring(11, 16)
    } else {
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val date = dateFormat.parse(this)
        val calendar = Calendar.getInstance()
        date?.let { calendar.time = it }
        calendar.timeZone = TimeZone.getTimeZone(timezone)

        return "${calendar.get(Calendar.HOUR_OF_DAY).toString().padStart(2, '0')}:${
            calendar.get(
                Calendar.MINUTE
            ).toString().padStart(2, '0')
        }"
    }
}

fun String.titleCaseFirstChar() = replaceFirstChar(Char::titlecase)