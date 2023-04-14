package ru.rlrent.domain.auth

import ru.rlrent.domain.entity.LoginInfoEntity
import ru.rlrent.domain.users.User

data class AuthUser(
    val user: User,
    val loginInfo: LoginInfoEntity
)
