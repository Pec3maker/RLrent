package ru.rlrent.ui.dialog.base

/**
 * Базовый интерфейс диалогов, возвращающих простой результат
 */
interface BaseDialogInterface : CoreDialog {

    var result: BaseResult

    val route: BaseDialogRoute

    fun dismiss()

    fun closeWithResult(result: BaseResult) {
        this.result = result
        dismiss()
    }
}
