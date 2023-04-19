package ru.rlrent.ui.util

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.view.View
import androidx.core.view.*
import androidx.core.view.WindowInsetsCompat.Type.ime

fun Activity.edgeToEdge(handling: () -> Unit) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        WindowCompat.setDecorFitsSystemWindows(window, false)

        window.statusBarColor = Color.TRANSPARENT
        window.navigationBarColor = Color.TRANSPARENT

        handling()
    } else {
        val flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        window.decorView.systemUiVisibility = flags
    }
}

fun Activity.setSystemBarColor(isColorDark: Boolean) {
    val windowInsetsController = WindowInsetsControllerCompat(window, window.decorView)
    windowInsetsController.isAppearanceLightNavigationBars = isColorDark
    windowInsetsController.isAppearanceLightStatusBars = isColorDark
}

fun Activity.showKeyboard() {
    val rootView = window.decorView.rootView
    WindowInsetsControllerCompat(window, rootView).show(ime())
}

fun Activity.hideKeyboard() {
    val rootView = window.decorView.rootView
    WindowInsetsControllerCompat(window, rootView).hide(ime())
}

fun Activity.onKeyboardChanges(callback: (height: Int) -> Unit) {
    val rootView = window.decorView.rootView
    var isKeyboardVisible = false

    ViewCompat.setOnApplyWindowInsetsListener(rootView) { _, insets ->
        val openingKeyboard = insets.isVisible(ime())

        if (isKeyboardVisible != openingKeyboard) {
            callback.invoke(insets.getInsets(ime()).bottom)
            isKeyboardVisible = openingKeyboard
        }

        insets
    }
}

infix fun View.paddingTo(insetType: Int) {

    val oldPaddingLeft = paddingLeft
    val oldPaddingTop = paddingTop
    val oldPaddingRight = paddingRight
    val oldPaddingBottom = paddingBottom

    ViewCompat.setOnApplyWindowInsetsListener(this) { view, insets ->

        val inset = insets.getInsets(insetType)

        view.updatePadding(
            left = oldPaddingLeft + inset.left,
            top = oldPaddingTop + inset.top,
            right = oldPaddingRight + inset.right,
            bottom = oldPaddingBottom + inset.bottom
        )

        insets
    }
}

infix fun View.marginTo(insetType: Int) {

    val oldMarginLeft = marginLeft
    val oldMarginTop = marginTop
    val oldMarginRight = marginRight
    val oldMarginBottom = marginBottom

    ViewCompat.setOnApplyWindowInsetsListener(this) { view, insets ->

        val inset = insets.getInsets(insetType)

        view.updateMargin(
            left = oldMarginLeft + inset.left,
            top = oldMarginTop + inset.top,
            right = oldMarginRight + inset.right,
            bottom = oldMarginBottom + inset.bottom
        )

        insets
    }
}