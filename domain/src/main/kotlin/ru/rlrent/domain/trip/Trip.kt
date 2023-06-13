package ru.rlrent.domain.trip

import ru.rlrent.domain.base.DOMAIN_EMPTY_STRING
import ru.rlrent.domain.trip.TripStatus.CLOSED

data class Trip(
    val id: Int = 0,
    val login: String = DOMAIN_EMPTY_STRING,
    val transportId: Int = 0,
    val worth: Double = 0.0,
    val startAt: String = DOMAIN_EMPTY_STRING,
    val finishAt: String = DOMAIN_EMPTY_STRING,
    val status: TripStatus = CLOSED
)