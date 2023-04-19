package com.thapl.ml.v_textfield.masks

import android.widget.EditText
import com.redmadrobot.inputmask.MaskedTextChangedListener
import com.thapl.ml.v_textfield.RawAndMaskedTextChangedListener
import java.lang.ref.WeakReference

/**
 * Класс, берущий на себя работу с масками ввода
 */
class MaskSupportDelegate : MaskedTextField {

    private lateinit var editText: WeakReference<EditText>

    private var formattedText: String = ""
    private var rawText: String = ""
    private var isMaskFilled: Boolean = false

    private var maskSupport: MaskedTextChangedListener? = null

    private var rawAndMaskedTextChangedListener: RawAndMaskedTextChangedListener? = null

    fun init(editText: EditText) {
        this.editText = WeakReference(editText)
    }

    fun setTextChangedListener(maskedTextChangedListener: RawAndMaskedTextChangedListener?) {
        this.rawAndMaskedTextChangedListener = maskedTextChangedListener
    }

    fun reactOnFocusChanges(hasFocus: Boolean) {
        maskSupport?.onFocusChange(editText.get(), hasFocus)
    }

    override fun setUpMask(maskFormat: String) {
        editText.get()?.let {
            setUpMask(
                MaskedTextChangedListener(
                    primaryFormat = maskFormat,
                    field = it
                )
            )
        }

    }

    override fun setUpMask(onMaskedTextChangedListener: MaskedTextChangedListener) {
        this.maskSupport = onMaskedTextChangedListener
        editText.get()?.addTextChangedListener(onMaskedTextChangedListener)
        observeChanges()
    }

    override fun getRawValue(): String = rawText

    override fun isMaskFilled(): Boolean = isMaskFilled

    private fun observeChanges() {
        maskSupport?.valueListener =
            object : MaskedTextChangedListener.ValueListener {
                override fun onTextChanged(
                    maskFilled: Boolean,
                    extractedValue: String,
                    formattedValue: String
                ) {
                    this@MaskSupportDelegate.isMaskFilled = maskFilled
                    this@MaskSupportDelegate.rawText = extractedValue
                    this@MaskSupportDelegate.formattedText = formattedValue
                    rawAndMaskedTextChangedListener?.invoke(
                        isMaskFilled,
                        extractedValue,
                        formattedValue
                    )
                }
            }
    }
}