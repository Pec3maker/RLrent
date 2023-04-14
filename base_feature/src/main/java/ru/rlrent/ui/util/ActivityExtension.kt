package ru.rlrent.ui.util

import android.app.Activity
import androidx.core.view.WindowInsetsControllerCompat

/**
 * Делает иконки в авигейшен-баре серыми, чтобы они были видны на белом фоне
 * Для Android 8, можно покрасить иконки в сервый, только программно.
 * Начиная с Android 8.1 мжно это сделать, через тему, с помощью атрибута.
 */
fun Activity.setNavigationBarIconsGray() {
    val wic = WindowInsetsControllerCompat(window, window.decorView)
    wic.isAppearanceLightNavigationBars = true
}