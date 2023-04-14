package ru.rlrent.ui.view.avatar

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import androidx.annotation.ColorInt
import androidx.annotation.Px
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.toRectF
import ru.surfstudio.android.template.base_feature.R
import ru.surfstudio.practice.base.utils.toPx
import java.lang.Integer.max

/**
 * View - для рисования круглой аватарки.
 */
class CircleAvatarImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    @Px
    var borderSize = DEFAULT_BORDER_SIZE.toPx

    @ColorInt
    var borderColor: Int = Color.WHITE

    private var resultBitmap: Bitmap? = null
    private var viewRect: Rect = Rect()
    private var borderRect: Rect = Rect()

    private val defaultPaint: Paint
    private val srcPaint: Paint
    private val borderPaint: Paint

    init {
        initAttrs(context, attrs)

        defaultPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        borderPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            strokeWidth = borderSize.toFloat()
            color = borderColor
        }
        srcPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        }
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

        initBitmap(w, h)
    }

    override fun onDraw(canvas: Canvas) {
        resultBitmap?.let { resultBm ->
            canvas.drawBitmap(resultBm, viewRect, viewRect, defaultPaint)
            canvas.drawOval(borderRect.toRectF(), borderPaint)
        }
    }

    private fun initBitmap(w: Int, h: Int) {
        val srcBitmap = drawable.toBitmap(w, h, Bitmap.Config.ARGB_8888)
        val circleBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ALPHA_8)
        val circleCanvas = Canvas(circleBitmap)
        circleCanvas.drawOval(viewRect.toRectF(), defaultPaint)

        resultBitmap = circleBitmap.copy(Bitmap.Config.ARGB_8888, true)
        val resultCanvas = Canvas(checkNotNull(resultBitmap))

        resultCanvas.drawBitmap(circleBitmap, viewRect, viewRect, null)
        resultCanvas.drawBitmap(srcBitmap, viewRect, viewRect, srcPaint)
    }

    private fun initAttrs(
        context: Context,
        attrs: AttributeSet?
    ) {
        with(context.obtainStyledAttributes(attrs, R.styleable.CircleAvatarImageView)) {
            borderSize =
                getDimension(
                    R.styleable.CircleAvatarImageView_avatar_border_size,
                    borderSize.toFloat()
                ).toInt()
            borderColor =
                getColor(R.styleable.CircleAvatarImageView_avatar_border_color, borderColor)
            recycle()
        }

        scaleType = ScaleType.CENTER_CROP
    }

    companion object {
        private const val DEFAULT_SIZE = 32
        private const val DEFAULT_BORDER_SIZE = 1
    }
}
