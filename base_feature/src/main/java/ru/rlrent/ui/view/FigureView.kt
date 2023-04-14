package ru.rlrent.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import ru.surfstudio.android.template.base_feature.R

/**
 * View - для рисования базовых фигур  на UI. (круг, квадрат, квадрат с закругленными краями)
 */
class FigureView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyles: Int = 0
) : View(context, attrs, defStyles) {

    @ColorInt
    var color = Color.WHITE
        set(value) {
            field = value
            paint.color = field
            invalidate()
        }

    var cornerRadius: Float = 0F
        set(value) {
            field = value
            invalidate()
        }
    private val paint: Paint = Paint(ANTI_ALIAS_FLAG)
    private var isCircle: Boolean = false

    init {
        with(context.obtainStyledAttributes(attrs, R.styleable.FigureView)) {
            isCircle = getBoolean(R.styleable.FigureView_figure_is_circle, false)
            cornerRadius =
                getDimensionPixelOffset(R.styleable.FigureView_figure_corner_radius, 0).toFloat()
            color = getColor(R.styleable.FigureView_figure_color, Color.WHITE)
            recycle()
        }
        paint.color = color
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (isCircle) {
            canvas.drawCircle(
                (width / 2).toFloat(),
                (height / 2).toFloat(),
                (width / 2).toFloat(),
                paint
            )
        } else {
            canvas.drawRoundRect(
                0F,
                0F,
                width.toFloat(),
                height.toFloat(),
                cornerRadius,
                cornerRadius,
                paint
            )
        }
    }
}
