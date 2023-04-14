package ru.rlrent.ui.view.avatar

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.Gravity
import androidx.annotation.ColorInt
import androidx.annotation.Px
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.graphics.toRectF
import ru.surfstudio.android.template.base_feature.R
import ru.surfstudio.practice.base.utils.toPx
import java.lang.Integer.max

/**
 * View - для рисования круглого счетчика/круглой иконки с буквой.
 */
class CircleTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyles: Int = 0
) : AppCompatTextView(context, attrs, defStyles) {

    @Px
    var borderSize = DEFAULT_BORDER_SIZE.toPx

    @ColorInt
    var borderColor: Int = Color.WHITE

    @ColorInt
    var circleColor: Int = Color.GRAY

    private var viewRect: Rect = Rect()
    private var borderRect: Rect = Rect()

    private val circlePaint: Paint
    private val borderPaint: Paint

    init {
        initAttrs(context, attrs)

        circlePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = circleColor
            style = Paint.Style.FILL
        }
        borderPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            strokeWidth = borderSize.toFloat()
            color = borderColor
        }

        textAlignment = TEXT_ALIGNMENT_CENTER
        gravity = Gravity.CENTER
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val size = if (
            MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.UNSPECIFIED ||
            MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.UNSPECIFIED
        ) {
            DEFAULT_SIZE.toPx
        } else {
            max(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec))
        }

        setMeasuredDimension(size, size)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        viewRect = Rect(0, 0, w, h)

        // calculate border inset
        borderRect = viewRect
        val borderInset = (borderSize / 2).toInt()
        viewRect.inset(borderInset, borderInset)
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawOval(viewRect.toRectF(), circlePaint)
        canvas.drawOval(borderRect.toRectF(), borderPaint)
        super.onDraw(canvas)
    }

    private fun initAttrs(
        context: Context,
        attrs: AttributeSet?
    ) {
        attrs?.let {
            with(context.obtainStyledAttributes(it, R.styleable.CircleTextView)) {
                borderSize = getDimension(
                    R.styleable.CircleTextView_border_size,
                    borderSize.toFloat()
                ).toInt()
                borderColor = getColor(R.styleable.CircleTextView_border_color, borderColor)
                circleColor = getColor(R.styleable.CircleTextView_circle_color, circleColor)
                recycle()
            }
        }
    }

    companion object {
        private const val DEFAULT_SIZE = 32
        private const val DEFAULT_BORDER_SIZE = 1
    }
}
