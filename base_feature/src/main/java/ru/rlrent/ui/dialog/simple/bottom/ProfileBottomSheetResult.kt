package ru.rlrent.ui.dialog.simple.bottom

import ru.surfstudio.practice.ui.dialog.base.BaseResult

/**
 * Результат работы bottom sheet диалога для экрана профиля.
 * @property DISMISS диалог отменен (по беку или по тапу за границу диалога)
 * @property CALL позвонить
 * @property MAIL написать
 */
enum class ProfileBottomSheetResult : BaseResult {
    DISMISS,
    CALL,
    MAIL
}
