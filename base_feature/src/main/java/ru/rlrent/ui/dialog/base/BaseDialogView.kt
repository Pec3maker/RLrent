package ru.rlrent.ui.dialog.base

import android.content.DialogInterface
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatDialogFragment
import ru.surfstudio.android.navigation.observer.command.EmitScreenResult

/**
 * Базовый простой диалог с возможностью возвращения результата.
 *
 * @property result результат работы экрана. Эмитится при его закрытии.
 * @property route route, служащая для открытия экрана, и для оповещения о результате.
 */
abstract class BaseDialogView : AppCompatDialogFragment(), BaseDialogInterface {

    override var result: BaseResult = SimpleResult.DISMISS

    @CallSuper
    override fun onDismiss(dialog: DialogInterface) {
        getCommandExecutor().execute(EmitScreenResult(route, result))
        super.onDismiss(dialog)
    }
}
