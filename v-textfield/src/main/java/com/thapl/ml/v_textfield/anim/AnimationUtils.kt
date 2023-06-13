package com.thapl.ml.v_textfield.anim

import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart

/**
 * Длительность анимации
 * поднятия/опускания хинта,
 * появления/ исчезновения подсказки под текстовым полем
 */
internal const val DEFAULT_TEXT_FIELD_ANIMATION_DURATION = 150L

const val DEFAULT_ANIMATION_DURATION = 450L

inline fun getValueAnimator(
    isForward: Boolean = true,
    duration: Long = DEFAULT_ANIMATION_DURATION,
    interpolator: TimeInterpolator = AccelerateDecelerateInterpolator(),
    crossinline doOnEnd: () -> Unit = {},
    crossinline doOnStart: () -> Unit = {},
    crossinline updateFloatListener: (progress: Float) -> Unit = {},
    crossinline updateIntListener: (progress: Int) -> Unit = {}
): ValueAnimator {
    val animator =
        if (isForward) ValueAnimator.ofFloat(0f, 1f)
        else ValueAnimator.ofFloat(1f, 0f)
    animator.addUpdateListener {
        val value = it.animatedValue as Float
        updateFloatListener(it.animatedValue as Float)
        updateIntListener(value.toInt())
    }
    animator.duration = duration
    animator.interpolator = interpolator
    animator.doOnEnd { doOnEnd.invoke() }
    animator.doOnStart { doOnStart.invoke() }
    return animator
}
