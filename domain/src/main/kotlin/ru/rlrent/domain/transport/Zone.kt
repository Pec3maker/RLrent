package ru.rlrent.domain.transport

import ru.rlrent.domain.base.DOMAIN_EMPTY_STRING

data class Zone(
    val id: Int = 0,
    val name: String = DOMAIN_EMPTY_STRING,
    val radius: Int = 0,
    val center: TransportPosition = TransportPosition()
)