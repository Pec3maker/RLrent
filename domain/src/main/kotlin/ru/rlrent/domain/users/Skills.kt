package ru.rlrent.domain.users

data class Skills(
    val mainSkill: MainSkill = MainSkill(),
    val otherSkills: List<String> = emptyList()
)