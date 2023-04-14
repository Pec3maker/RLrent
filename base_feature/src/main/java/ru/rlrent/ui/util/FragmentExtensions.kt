package ru.rlrent.ui.util

import androidx.fragment.app.Fragment
import ru.surfstudio.practice.ui.util.back_press.DefaultBackPressedCallback

fun Fragment.addDefaultOnBackPressedCallback(onBackPressed: () -> Unit) {
    requireActivity().onBackPressedDispatcher.addCallback(this, DefaultBackPressedCallback(onBackPressed))
}

/**
 * Hides Keyboard when click on empty place
 */
fun Fragment.hideKeyboardOutsideEditTextTouch() {
    requireView().setOnClickListener {
        hideKeyboard()
    }
}
