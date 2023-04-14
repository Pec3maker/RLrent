package ru.rlrent.domain.users

import ru.rlrent.domain.base.DOMAIN_EMPTY_STRING

data class MainSkill(
    val name: String = DOMAIN_EMPTY_STRING,
    val textColor: String = DOMAIN_EMPTY_STRING,
    val backgroundColor: String = DOMAIN_EMPTY_STRING
)