package com.thapl.ml.v_textfield.anim.underline

/**
 * Интерфейс, описывающий аниматор, который ответственен за показ/скрытие подсказки под полем
 */
interface UnderlineTextAnimator {

    /**
     * Переключить из состояния показа ошибки в обычное состояние
     */
    fun changeStateToNormal(helperText: String)

    /**
     * Показать ошибку
     */
    fun changeStateToError(errorText: String)

    /**
     * Сменить текст подсказки
     */
    fun changeHelperText(helperText: String)
}
