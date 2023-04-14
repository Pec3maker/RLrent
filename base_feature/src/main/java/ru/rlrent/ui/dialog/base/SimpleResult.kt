package ru.rlrent.ui.dialog.base

import ru.surfstudio.practice.ui.dialog.base.SimpleResult.*

/**
 * Результат работы простого диалога.
 *
 * @property POSITIVE нажата кнопка ОК
 * @property NEGATIVE нажата кнопка Отмены
 * @property DISMISS диалог отменен (по беку или по тапу за границу диалога)
 */
enum class SimpleResult : BaseResult {
    POSITIVE,
    NEGATIVE,
    DISMISS
}
