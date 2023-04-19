package com.thapl.ml.v_textfield.anim.hint

import android.widget.TextView
import com.thapl.ml.v_textfield.anim.DEFAULT_TEXT_FIELD_ANIMATION_DURATION
import com.thapl.ml.v_textfield.data.HintAnimationParams

/**
 * Класс с анимациями актуальными для подсказки над полем полем со стилем Filled или Plain
 *
 * @property targetView - view для отображения подписи, которая будет анимироваться
 * @property boxHeight - высота родительского контейнера с EditText и targetView
 * @property hintAnimationParams - описание положения и внешнего вида подписи
 */
class HintRaisingAnimator(
    private val targetView: TextView,
    private val boxHeight: Float,
    private val hintAnimationParams: HintAnimationParams
) : HintAnimator {

    init {
        targetView.pivotX = 0f
    }

    override fun setInitialState(isExpanded: Boolean) {
        if (isExpanded) {
            initViewsInExpandedState()
        } else {
            initViewsInCollapsedState()
        }
    }

    override fun animateFocusChanges(hasFocus: Boolean, isTextEmpty: Boolean) {
        when {
            hasFocus && isInCollapsedState() -> animateHintExpandedState()
            !hasFocus && isTextEmpty -> animateHintCollapsedState()
        }
    }

    override fun animateTextChanged(hasFocus: Boolean, isTextEmpty: Boolean) {
        when {
            hasFocus -> return
            isTextEmpty -> animateHintCollapsedState()
            else -> animateHintExpandedState()
        }
    }

    /**
     * Устанавливает у вью параметры, соответствующие "поднятому" над текстом состоянию
     */
    private fun initViewsInExpandedState() {
        with(targetView) {
            translationY = hintAnimationParams.marginTop
            scaleX = hintAnimationParams.focusedTextScale
            scaleY = hintAnimationParams.focusedTextScale
        }
    }

    /**
     * Устанавливает у вью параметры, соответствующие замещающему текст состоянию
     */
    private fun initViewsInCollapsedState() {
        with(targetView) {
            translationY = calculateCollapsedHintY()
            scaleX = 1f
            scaleY = 1f
        }
    }

    /**
     * Приводит хинт в состояние, когда от замещает собой пустой текст
     */
    private fun animateHintCollapsedState() {
        targetView.animate()
            .scaleX(1f)
            .scaleY(1f)
            .translationY(calculateCollapsedHintY())
            .setDuration(DEFAULT_TEXT_FIELD_ANIMATION_DURATION)
            .start()
    }

    /**
     * Приводит хинт в состояние, когда от поднят над текстом
     */
    private fun animateHintExpandedState() {
        with(hintAnimationParams) {
            targetView.animate()
                .scaleX(focusedTextScale)
                .scaleY(focusedTextScale)
                .translationY(marginTop)
                .setDuration(DEFAULT_TEXT_FIELD_ANIMATION_DURATION)
                .start()
        }
    }

    /**
     * Расчитывает позицию вью, чтобы она была по середине контейнера
     */
    private fun calculateCollapsedHintY(): Float {
        val backgroundHeight = boxHeight
        val hintHeight = targetView.height
        return backgroundHeight / 2f - hintHeight / 2f
    }

    private fun isInCollapsedState(): Boolean {
        return targetView.translationY == calculateCollapsedHintY()
    }
}
