package com.thapl.ml.v_textfield

import android.content.Context
import android.text.method.PasswordTransformationMethod
import android.text.method.SingleLineTransformationMethod
import android.util.AttributeSet
import com.thapl.ml.f_textfield.R
import com.thapl.ml.v_textfield.masks.MaskSupportDelegate
import ru.surfstudio.android.utilktx.ktx.ui.view.setFocusAndCursorToEnd

class PasswordInputField @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.textFieldStyle,
    defStyleRes: Int = R.style.AppTheme_TextInputField,
    maskSupportDelegate: MaskSupportDelegate = MaskSupportDelegate()
) : TextInputField(context, attrs, defStyleAttr, defStyleRes, maskSupportDelegate) {

    override fun initEndImageButtonListener() {
        endImageButton.setOnClickListener {
            it.isSelected = !it.isSelected
            editText.transformationMethod =
                if (it.isSelected) SingleLineTransformationMethod.getInstance() else PasswordTransformationMethod.getInstance()
            if (isFocused) editText.setFocusAndCursorToEnd()
            onEndImageButtonClickListener?.invoke()
        }
    }
}
