package ru.rlrent.ui.dialog.base

import android.content.DialogInterface
import androidx.annotation.CallSuper
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.surfstudio.android.navigation.observer.command.EmitScreenResult

/**
 * Базовая реализация bottom sheet диалога, возвращающего простой результат
 */
abstract class BaseBottomSheetDialogView : BottomSheetDialogFragment(), BaseDialogInterface {

    abstract override var result: BaseResult

    @CallSuper
    override fun onDismiss(dialog: DialogInterface) {
        getCommandExecutor().execute(EmitScreenResult(route, result))
        super.onDismiss(dialog)
    }
}
