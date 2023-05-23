package ru.rlrent.i_storage.theme

/**
 * Локальное хранилище текущей темы
 * */
interface ThemeTypeStorage {
    var currentThemeType: ThemeType?
    fun clear()
}
