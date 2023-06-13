package com.thapl.ml.v_textfield.anim.underline

import android.widget.TextView
import androidx.core.view.isVisible
import com.thapl.ml.v_textfield.anim.DEFAULT_TEXT_FIELD_ANIMATION_DURATION

/**
 * Класс, отвечающий за показ и скрытие текстового поля под EditText,
 * это поле используется для показа подсказки или текста ошибки
 */
class BaseUnderlineTextAnimator(
    private val targetView: TextView,
    private val visibilityWhileHidden: Int
) : UnderlineTextAnimator {

    init {
        // если у поля нет подсказки, то прячем поле, чтобы при появлении подсказки или ошибки
        // анимировать его появление сверху
        if (targetView.text.isEmpty()) {
            targetView.visibility = visibilityWhileHidden
            targetView.translationY = -targetView.height.toFloat() / 2f
        }
    }

    override fun changeStateToNormal(helperText: String) {
        if (helperText.isEmpty()) {
            hideUnderlineText()
        }
        targetView.text = helperText
    }

    override fun changeStateToError(errorText: String) {
        targetView.text = errorText
        if (!targetView.isVisible) {
            showUnderlineText()
        }
    }

    override fun changeHelperText(helperText: String) {
        // для пустого хелпера анимированно убираем старое значение
        if (helperText.isEmpty()) {
            hideUnderlineText(endAction = { targetView.text = helperText })
            return
        }

        targetView.text = helperText

        if (!targetView.isVisible) {
            showUnderlineText()
        }
    }

    private fun showUnderlineText() {
        targetView.animate()
            .withStartAction {
                targetView.isVisible = true
                targetView.alpha = 0f
            }
            .setDuration(DEFAULT_TEXT_FIELD_ANIMATION_DURATION)
            .alpha(1f)
            .translationY(0f)
            .start()
    }

    private fun hideUnderlineText(endAction: () -> Unit = {}) {
        targetView.animate()
            .withEndAction {
                targetView.visibility = visibilityWhileHidden
                endAction()
            }
            .setDuration(DEFAULT_TEXT_FIELD_ANIMATION_DURATION)
            .alpha(0f)
            .translationY(-targetView.height.toFloat() / 2f)
            .start()
    }
}
