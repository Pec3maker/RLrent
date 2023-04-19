package com.thapl.ml.v_textfield.attrs

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.text.InputType
import com.thapl.ml.f_textfield.R
import com.thapl.ml.v_textfield.data.HintAnimationParams
import com.thapl.ml.v_textfield.data.TextInputFieldState

data class TextInputFieldAttributes(
    val text: String,
    val textAppearance: Int,
    val editTextTextColor: ColorStateList,
    val inputType: Int,
    val hintText: String,
    val hintTextColor: ColorStateList,
    val hintAnimationParams: HintAnimationParams,
    val errorText: String,
    val helperText: String,
    val helperTextColor: ColorStateList,
    val backgroundLineColor: ColorStateList,
    val boxColor: ColorStateList,
    val state: TextInputFieldState,
    val useFixedSpaceForUnderlineText: Boolean,
    val underlineViewHorizontalPadding: Float,
    val endImageButtonIcon: Drawable?,
) {

    companion object {
        val TEXT_INPUT_FIELD_ERROR = intArrayOf(R.attr.view_state_error, android.R.attr.state_enabled)
        val TEXT_INPUT_FIELD_FOCUSED = intArrayOf(R.attr.view_state_focused, android.R.attr.state_enabled)
        val TEXT_INPUT_FIELD_DISABLED = intArrayOf(-android.R.attr.state_enabled)
        val TEXT_INPUT_FIELD_DEFAULT = intArrayOf()

        // возможные состояния текстового поля
        val TEXT_INPUT_FIELD_STATES = arrayOf(
            TEXT_INPUT_FIELD_ERROR,
            TEXT_INPUT_FIELD_FOCUSED,
            TEXT_INPUT_FIELD_DISABLED,
            TEXT_INPUT_FIELD_DEFAULT
        )

        const val UNDEFINED_VALUE = -1
        const val DEFAULT_INPUT_TYPE = InputType.TYPE_CLASS_TEXT
        const val DEFAULT_BOX_COLOR = Color.WHITE
    }
}
