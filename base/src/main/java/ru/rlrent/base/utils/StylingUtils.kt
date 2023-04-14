package ru.rlrent.base.utils

import android.content.Context
import android.content.res.Resources
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes

/**
 * Получить значение атрибута цвета из темы
 */
@ColorInt
fun getThemeAttrColor(
    context: Context,
    @AttrRes colorAttr: Int,
    @ColorRes defaultColor: Int = android.R.color.white
): Int {
    context.withStyledAttributes(null, intArrayOf(colorAttr)) {
        return getColor(0, ContextCompat.getColor(context, defaultColor))
    }
    return ContextCompat.getColor(context, defaultColor)
}

val Int.toDp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()

val Int.toPx: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

val Int.toSp: Int
    get() = (this / Resources.getSystem().displayMetrics.scaledDensity).toInt()

val Float.toSp: Float
    get() = (this / Resources.getSystem().displayMetrics.scaledDensity)

val Float.toPx: Float
    get() = (this * Resources.getSystem().displayMetrics.density)
