package com.thapl.ml.v_textfield.utils

import android.text.Editable
import android.text.TextWatcher

/**
 * Обертка над стандартным TextWatcher
 */
class DefaultTextWatcher(
    val beforeTextChangedListener: (s: CharSequence?, start: Int, count: Int, after: Int) -> Unit = { _, _, _, _ -> },
    val onTextChangedListener: (s: CharSequence?, start: Int, before: Int, count: Int) -> Unit = { _, _, _, _ -> },
    val afterTextChangedListener: (s: Editable?) -> Unit = {},
) : TextWatcher {

    override fun beforeTextChanged(
        s: CharSequence?,
        start: Int,
        count: Int,
        after: Int
    ) {
        beforeTextChangedListener(s, start, count, after)
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        onTextChangedListener(s, start, before, count)
    }

    override fun afterTextChanged(s: Editable?) {
        afterTextChangedListener(s)
    }
}