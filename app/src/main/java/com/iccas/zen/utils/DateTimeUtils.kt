package com.iccas.zen.utils

import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

fun formatLocalDateTime(dateTimeString: String, pattern: String = "yyyy-MM-dd"): String {
    val localDateTime = LocalDateTime.parse(dateTimeString, DateTimeFormatter.ISO_DATE_TIME)
    val formatter = DateTimeFormatter.ofPattern(pattern)
    return localDateTime.format(formatter)
}

fun formatDuration(durationString: String): String {
    return try {
        val duration = Duration.parse(durationString)
        val hours = duration.toHours()
        val minutes = duration.toMinutes() % 60
        val seconds = duration.seconds % 60

        val formattedDuration = StringBuilder()

        if (hours > 0) {
            formattedDuration.append("${hours}h ")
        }
        if (minutes > 0) {
            formattedDuration.append("${minutes}m ")
        }
        if (seconds > 0) {
            formattedDuration.append("${seconds}s ")
        }

        if (formattedDuration.isEmpty()) {
            "0s"
        } else {
            formattedDuration.toString().trim()
        }
    } catch (e: DateTimeParseException) {
        "Invalid duration"
    }
}

