package com.thapl.ml.v_textfield.masks

import com.redmadrobot.inputmask.MaskedTextChangedListener

/**
 * Интерфейс для работы с маскированным вводом
 */
interface MaskedTextField {

    /**
     * Возвращает текст без маскирования
     * Пример: Для поля ввода телефона с текстом +7 (980) 249-75-51 extractedValue=9802497551
     */
    fun getRawValue(): String

    /**
     * Возвращает заполнена ли вся маска или текст введен только частично
     */
    fun isMaskFilled(): Boolean

    fun setUpMask(maskFormat: String)

    fun setUpMask(onMaskedTextChangedListener: MaskedTextChangedListener)
}