package ru.rlrent.domain.entity

/**
 * сущность, представляющая информацию о токене и его времени жизни
 */
data class LoginInfoEntity(
    val accessToken: String,
    val refreshToken: String?
)
