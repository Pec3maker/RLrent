package com.thapl.ml.v_textfield.data

import android.os.Parcelable
import android.view.View
import kotlinx.parcelize.Parcelize

/**
 * Объект для сохранения состояния поля ввода
 *
 * @param state состояние поля
 * @param superSavedState родительское сохраненное состояние
 */
@Parcelize
open class TextInputFieldSavedState(
    val state: TextInputFieldState,
    val superSavedState: Parcelable?
) : View.BaseSavedState(superSavedState)
