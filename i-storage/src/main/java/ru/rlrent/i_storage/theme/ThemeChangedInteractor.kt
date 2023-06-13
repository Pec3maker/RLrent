package ru.rlrent.i_storage.theme

import androidx.appcompat.app.AppCompatDelegate
import javax.inject.Inject

class ThemeChangedInteractor @Inject constructor(
    private val themeTypeStorage: ThemeTypeStorage
) {

    val currentTheme: ThemeType?
        get() = themeTypeStorage.currentThemeType

    fun setLightTheme() {
        themeTypeStorage.currentThemeType = ThemeType.LIGHT
        activateSavedTheme()
    }

    fun setDarkTheme() {
        themeTypeStorage.currentThemeType = ThemeType.DARK
        activateSavedTheme()
    }

    fun activateSavedTheme() {
        when (themeTypeStorage.currentThemeType) {
            ThemeType.DARK -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}
