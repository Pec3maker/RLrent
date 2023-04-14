package ru.rlrent.domain.users

import ru.rlrent.domain.base.DOMAIN_EMPTY_STRING
import java.io.Serializable

data class User(
    val id: Int = 0,
    val avatar: String = DOMAIN_EMPTY_STRING,
    val firstName: String = DOMAIN_EMPTY_STRING,
    val lastName: String = DOMAIN_EMPTY_STRING,
    val dateOfBirth: String = DOMAIN_EMPTY_STRING,
    val phone: String = DOMAIN_EMPTY_STRING,
    val email: String = DOMAIN_EMPTY_STRING,
    val city: String = DOMAIN_EMPTY_STRING,
    val position: String = DOMAIN_EMPTY_STRING,
    val startWorkDay: String = DOMAIN_EMPTY_STRING,
    val department: String = DOMAIN_EMPTY_STRING,
    val currentProjects: List<CurrentProject> = emptyList(),
    val countProjectsParticipate: Int = 0,
    val skills: Skills = Skills()
) : Serializable
