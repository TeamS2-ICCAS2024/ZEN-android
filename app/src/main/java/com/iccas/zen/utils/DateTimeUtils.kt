package com.iccas.zen.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun formatLocalDateTime(dateTimeString: String): String {
    val localDateTime = LocalDateTime.parse(dateTimeString, DateTimeFormatter.ISO_DATE_TIME)
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    return localDateTime.format(formatter)
}
