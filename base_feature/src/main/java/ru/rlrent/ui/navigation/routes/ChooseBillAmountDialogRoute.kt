package ru.rlrent.ui.navigation.routes

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import ru.rlrent.ui.dialog.base.BaseSimpleDialogRoute
import ru.surfstudio.android.core.ui.navigation.Route
import ru.rlrent.ui.dialog.choose_bill_amount.ChooseBillAmountFragment

/**
 * Роут для открытия диалога с выбором интервала для настроек
 */
class ChooseBillAmountDialogRoute(
    override val dialogId: String
) : BaseSimpleDialogRoute() {

    constructor(bundle: Bundle) : this(
        bundle[Route.EXTRA_FIRST] as String
    )

    override fun getScreenClass(): Class<out DialogFragment> {
        return ChooseBillAmountFragment::class.java
    }

    override fun prepareData(): Bundle = Bundle().apply {
        putString(Route.EXTRA_FIRST, dialogId)
    }
}