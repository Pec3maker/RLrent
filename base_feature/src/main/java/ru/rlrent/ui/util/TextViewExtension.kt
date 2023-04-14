package ru.rlrent.ui.util

import android.text.TextUtils
import android.widget.TextView

var TextView.distinctText: CharSequence
    get() = text
    set(value) {
        setTextDistinct(value)
    }

fun TextView.setTextDistinct(newText: CharSequence): Boolean {
    if (TextUtils.equals(newText, text)) return false
    text = newText.toString()
    return true
}
