package com.thapl.ml.v_textfield.styling

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.InsetDrawable
import android.graphics.drawable.LayerDrawable
import android.view.Gravity
import ru.rlrent.base.utils.toDp
import ru.rlrent.base.utils.toPx

/**
 * Класс, создающий фон для текстового поля стиля Filled
 * с поддержкой разных цветов для разных состояний View
 */
class FilledBackground(
    private val lineColorStateList: ColorStateList,
    private val boxColor: ColorStateList,
    private val lineHeight: Int = 1.toPx,
    private val underlineRadius: Float = 2.toPx.toFloat(),
    private val underlineViewPadding: Float
) {

    fun createBackground(): Drawable {

        val line = InsetDrawable(GradientDrawable().apply {
            cornerRadius = underlineRadius
            shape = GradientDrawable.RECTANGLE
            setTintList(lineColorStateList)
        }, underlineViewPadding.toInt(), 0, underlineViewPadding.toInt(), 0)

        val box = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = 16.toPx.toFloat()
            setTintList(boxColor)
        }

        return LayerDrawable(arrayOf(box, line)).apply {
            setLayerGravity(1, Gravity.BOTTOM)
            setLayerHeight(1, lineHeight)
        }
    }
}
