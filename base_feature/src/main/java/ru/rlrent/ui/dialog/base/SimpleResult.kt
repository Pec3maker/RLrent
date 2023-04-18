package ru.rlrent.ui.dialog.base

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
