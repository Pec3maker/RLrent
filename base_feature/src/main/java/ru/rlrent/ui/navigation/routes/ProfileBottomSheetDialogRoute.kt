package ru.rlrent.ui.navigation.routes

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import ru.surfstudio.android.navigation.route.Route
import ru.surfstudio.android.utilktx.ktx.text.EMPTY_STRING
import ru.surfstudio.practice.ui.dialog.base.BaseDialogRoute
import ru.surfstudio.practice.ui.dialog.simple.bottom.ProfileBottomSheetDialogView

/**
 * Роут боттом-шита с 2 кнопками
 *
 */
class ProfileBottomSheetDialogRoute(
    override val dialogId: String,
    val phone: String = EMPTY_STRING,
) : BaseDialogRoute() {

    constructor(arguments: Bundle) : this(
        arguments.getString(Route.EXTRA_FIRST) ?: EMPTY_STRING,
        arguments.getString(Route.EXTRA_SECOND) ?: EMPTY_STRING
    )

    override fun getScreenClass(): Class<out DialogFragment>? {
        return ProfileBottomSheetDialogView::class.java
    }

    override fun prepareData(): Bundle = Bundle().apply {
        putString(Route.EXTRA_FIRST, dialogId)
        putString(Route.EXTRA_SECOND, phone)
    }
}
