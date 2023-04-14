package ru.rlrent.ui.util

import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter

private val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

fun String.toAge(): Int {
    val date = LocalDate.parse(this, formatter)
    val now = LocalDate.now()
    return Period.between(date, now).years
}

fun String.toMonths(): Int {
    val date = LocalDate.parse(this, formatter)
    val now = LocalDate.now()
    return Period.between(date, now).months
}
