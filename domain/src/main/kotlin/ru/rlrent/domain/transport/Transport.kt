package ru.rlrent.domain.transport

import ru.rlrent.domain.base.DOMAIN_EMPTY_STRING

data class Transport(
    val id: Int = 0,
    val code: String = DOMAIN_EMPTY_STRING,
    val status: TransportStatus = TransportStatus.FREE,
    val transportCondition: TransportCondition = TransportCondition.GOOD,
    val transportType: TransportType = TransportType.ELECTRIC_SCOOTER,
    val manufacturer: String = DOMAIN_EMPTY_STRING,
    val position: TransportPosition = TransportPosition(),
    val maxSpeed: Int = 0,
    val charge: Int = 0
)
