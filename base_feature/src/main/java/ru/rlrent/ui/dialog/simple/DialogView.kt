package ru.rlrent.ui.dialog.simple

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import ru.surfstudio.android.core.mvi.impls.ui.dialog.standard.EMPTY_RES
import ru.surfstudio.android.navigation.observer.ScreenResultObserver
import ru.surfstudio.android.navigation.rx.extension.observeScreenResult
import ru.surfstudio.practice.base.utils.toPx
import ru.surfstudio.practice.ui.dialog.base.BaseDialogView
import ru.surfstudio.practice.ui.dialog.base.SimpleResult

/**
 * Диалог позволяющий получить [SimpleResult]
 * Для получения результата использовать [observeScreenResult] у [ScreenResultObserver]
 */
class DialogView : BaseDialogView() {

    override val route: DialogRoute by lazy {
        DialogRoute(requireArguments())
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        isCancelable = route.isCancelable

        return MaterialAlertDialogBuilder(requireActivity())
            .setTitle(if (route.titleRes != EMPTY_RES) getString(route.titleRes) else route.title)
            .setMessage(if (route.messageRes != EMPTY_RES) getString(route.messageRes) else route.message)
            .setPositiveButton(getString(route.positiveBtnTextRes)) { _, _ -> closeWithResult(SimpleResult.POSITIVE) }
            .setNegativeButton(getString(route.negativeBtnTextRes)) { _, _ -> closeWithResult(SimpleResult.NEGATIVE) }
            .create()
            .apply {
                // красим кнопку только когда диалог будет показан
                // потому что до этого момента кнопка может быть не создана
                setOnShowListener {
                    val positiveColor = ContextCompat.getColor(requireContext(), route.positiveBtnColorRes)
                    val negativeColor = ContextCompat.getColor(requireContext(), route.negativeBtnColorRes)
                    getButton(AlertDialog.BUTTON_POSITIVE).apply {
                        setPadding(8.toPx)
                        setTextColor(positiveColor)
                    }
                    getButton(AlertDialog.BUTTON_NEGATIVE).apply {
                        setPadding(8.toPx)
                        setTextColor(negativeColor)
                    }
                }
            }
    }
}
