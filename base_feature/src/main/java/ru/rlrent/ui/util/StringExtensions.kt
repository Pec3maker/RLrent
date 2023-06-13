package ru.rlrent.ui.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

fun String.convertToCustomDateFormat(): String {
    val inputFormat = DateTimeFormatter.ISO_DATE_TIME
    val outputFormat = DateTimeFormatter.ofPattern("d MMMM", Locale("ru"))
    val dateTime = LocalDateTime.parse(this, inputFormat)
    return dateTime.format(outputFormat)
}