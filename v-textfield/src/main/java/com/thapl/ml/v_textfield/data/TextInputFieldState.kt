package com.thapl.ml.v_textfield.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Набор классов для описания состояния текстового поля
 */
sealed class TextInputFieldState(val id: Int) : Parcelable {
    @Parcelize
    object Normal : TextInputFieldState(0)

    @Parcelize
    object Disabled : TextInputFieldState(1)

    @Parcelize
    object Error : TextInputFieldState(2)

    internal companion object {
        fun fromAttr(attrValue: Int): TextInputFieldState {
            return when (attrValue) {
                Normal.id -> Normal
                Disabled.id -> Disabled
                Error.id -> Error
                else -> Normal
            }
        }
    }

    val isError: Boolean
        get() = this is Error

    val isNormal: Boolean
        get() = this is Normal

    val isEnabled: Boolean
        get() = this !is Disabled
}