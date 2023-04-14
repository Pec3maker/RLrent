package ru.rlrent.ui.util

import android.app.Activity
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat.Type.ime
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment

/**
 * Checks is keyboard visible right now
 */
fun Fragment.isKeyboardVisible(): Boolean = requireView().isInsetVisible(ime())

/**
 * Shows the soft keyboard
 */
fun Fragment.showKeyboard() {
    requireView().showKeyboard()
}

/**
 * Hides the soft keyboard
 */
fun Fragment.hideKeyboard() {
    requireView().hideKeyboard()
}

/**
 * Shows the soft keyboard
 */
fun View.showKeyboard() {
    val activity = context as? Activity ?: return
    val controller = WindowInsetsControllerCompat(activity.window, this)
    controller.show(ime())
}

/**
 * Hides the soft keyboard
 */
fun View.hideKeyboard() {
    val activity = context as? Activity ?: return
    val controller = WindowInsetsControllerCompat(activity.window, this)
    controller.hide(ime())
}

/**
 * Checks is inset with [insetType] visible right now
 */
private fun View.isInsetVisible(insetType: Int): Boolean {
    val insets = ViewCompat.getRootWindowInsets(this)
    return insets?.isVisible(insetType) == true
}
