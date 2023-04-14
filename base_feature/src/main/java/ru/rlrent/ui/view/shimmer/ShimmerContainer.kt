package ru.rlrent.ui.view.shimmer

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout

class ShimmerContainer @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    private var animator: ObjectAnimator =
        ObjectAnimator.ofFloat(this, "alpha", MIN_ALPHA, MAX_ALPHA)

    init {
        animator.repeatCount = ValueAnimator.INFINITE
        animator.repeatMode = ValueAnimator.REVERSE
        animator.duration = DEFAULT_DURATION
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        animator.start()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        animator.cancel()
    }

    companion object {
        private const val MIN_ALPHA = 0.7f
        private const val MAX_ALPHA = 1f
        private const val DEFAULT_DURATION = 400L
    }
}
