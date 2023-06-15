package ru.rlrent.ui.dialog.choose_bill_amount

import ru.rlrent.ui.mvi.navigation.event.NavCommandsComposition
import ru.rlrent.ui.mvi.navigation.event.NavCommandsEvent
import ru.surfstudio.android.core.mvi.event.Event
import ru.surfstudio.android.core.mvi.event.RequestEvent
import ru.surfstudio.android.core.mvi.event.lifecycle.LifecycleEvent
import ru.surfstudio.android.core.mvp.binding.rx.request.Request
import ru.surfstudio.android.core.ui.state.LifecycleStage

internal sealed class ChooseBillAmountEvent : Event {

    data class Navigation(override var event: NavCommandsEvent = NavCommandsEvent()) :
        NavCommandsComposition, ChooseBillAmountEvent()

    data class Lifecycle(override var stage: LifecycleStage) : ChooseBillAmountEvent(),
        LifecycleEvent

    sealed class Input : ChooseBillAmountEvent() {

        data class SetBillAmountClicked(val chosenTimeIndex: Int) : Input()

        data class MakePayment(val token: String) : Input()
    }

    data class RequestPayment(override val request: Request<Unit>) : RequestEvent<Unit>,
        ChooseBillAmountEvent()
}
