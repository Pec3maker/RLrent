package ru.rlrent.ui.util

import androidx.fragment.app.Fragment
import ru.rlrent.ui.util.back_press.DefaultBackPressedCallback

fun Fragment.addDefaultOnBackPressedCallback(onBackPressed: () -> Unit) {
    requireActivity().onBackPressedDispatcher.addCallback(
        this,
        DefaultBackPressedCallback(onBackPressed)
    )
}
