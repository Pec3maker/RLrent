package ru.rlrent.domain.user

import ru.rlrent.domain.base.DOMAIN_EMPTY_STRING
import java.io.Serializable

data class User(
    val id: Int = 0,
    val role: UserRole = UserRole.USER,
    val login: String = DOMAIN_EMPTY_STRING,
    val imageUrl: String = DOMAIN_EMPTY_STRING,
    val firstName: String = DOMAIN_EMPTY_STRING,
    val email: String = DOMAIN_EMPTY_STRING,
    val tripsCount: Int = 0,
    val tripsCost: Int = 0,
    val registrationDate: String = DOMAIN_EMPTY_STRING,
    val phoneNumber: String = DOMAIN_EMPTY_STRING,
    val bill: String = DOMAIN_EMPTY_STRING
) : Serializable
