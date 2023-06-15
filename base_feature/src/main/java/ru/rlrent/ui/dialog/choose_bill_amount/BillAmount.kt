package ru.rlrent.ui.dialog.choose_bill_amount

sealed class BillAmount(
    val timeIntervalMax: Int,
    val timeIntervalMin: Int,
    val timeIntervalStep: Int
) {

    val displayValues =
        (timeIntervalMin..timeIntervalMax step timeIntervalStep).map { it.toString() }

    /**
     * Интервал оплаты
     * */
    object PayInterval : BillAmount(
        timeIntervalMax = 1000,
        timeIntervalMin = 100,
        timeIntervalStep = 100
    )
}
