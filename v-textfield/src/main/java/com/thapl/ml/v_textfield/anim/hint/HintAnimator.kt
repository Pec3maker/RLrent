package com.thapl.ml.v_textfield.anim.hint

/**
 * Интерфейс, описывающий аниматор, который ответственен за показ/скрытие подписи над полем
 */
interface HintAnimator {
    /**
     *   Метод, для подготовки View  последующим анимациям
     *   @param isExpanded - поднята ли view над EditText
     */
    fun setInitialState(isExpanded: Boolean)

    /**
     * Реакция на изменения фокуса в EditText
     */
    fun animateFocusChanges(hasFocus: Boolean, isTextEmpty: Boolean)

    /**
     * Реакция на изменения текста в EditText
     */
    fun animateTextChanged(hasFocus: Boolean, isTextEmpty: Boolean)
}
