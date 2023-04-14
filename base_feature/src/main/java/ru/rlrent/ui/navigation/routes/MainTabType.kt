package ru.rlrent.ui.navigation.routes

import java.io.Serializable

/**
 * Типы табов на главном экране MainActivityView
 * TODO переименовать табы и добавить свои при необходимости
 */
enum class MainTabType(val position: Int): Serializable {
    PROJECTS(0), // экран проекты
    EMPLOYEES(1), // экран сотрудники
    ABOUT(2); // экран о приложении

    companion object {
        fun getByValue(position: Int) = values().first { it.position == position }
    }
}