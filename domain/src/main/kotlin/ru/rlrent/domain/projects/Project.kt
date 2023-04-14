package ru.rlrent.domain.projects

import ru.rlrent.domain.base.DOMAIN_EMPTY_STRING
import java.io.Serializable

data class Project(
    val id: Int = 0,
    val name: String = DOMAIN_EMPTY_STRING,
    val description: String = DOMAIN_EMPTY_STRING,
    val countMembers: Int = 0,
    val iconUrl: String = DOMAIN_EMPTY_STRING,
    val daysCount: Int = 0,
    val avatarUrls: List<String> = emptyList()
) : Serializable
