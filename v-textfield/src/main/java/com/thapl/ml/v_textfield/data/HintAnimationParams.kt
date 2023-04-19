package com.thapl.ml.v_textfield.data

/**
 * Класс для описания внешнего вида подписи над полем ввода
 *
 * @property marginTop - отступ от верхнего края родительского контейнера, наивысшая точка для анимации
 * @param textSizeFocused - размер текста в свернутом состоянии
 * @param textSizeNotFocused - размер текста в состоянии, когда view с подсказкой замещает собой пустой EditText
 */
class HintAnimationParams(
    val marginTop: Float,
    private val textSizeFocused: Float,
    private val textSizeNotFocused: Float
) {
    val focusedTextScale = textSizeFocused / textSizeNotFocused
}