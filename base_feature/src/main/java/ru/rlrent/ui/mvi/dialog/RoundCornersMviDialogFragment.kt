package ru.rlrent.ui.mvi.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import ru.surfstudio.android.core.mvi.event.Event

/**
 * Диалог с сущностями MVi, имеющий круглые края
 */
abstract class RoundCornersMviDialogFragment<S, E : Event> : BaseMviDialogFragment<S, E>() {

    /**
     * Необходимо вызвать внутри метода [onCreateView].
     *
     * Данный метод делает прозрачный фон у окна, чтобы была возможность
     * задать круглые края у диалога в вёрстке.
     * */
    protected fun initTransparentWindowBackground() {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
}
