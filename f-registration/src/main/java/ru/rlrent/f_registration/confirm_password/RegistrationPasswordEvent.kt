package ru.rlrent.f_registration.confirm_password

import ru.rlrent.ui.mvi.navigation.event.NavCommandsComposition
import ru.rlrent.ui.mvi.navigation.event.NavCommandsEvent
import ru.surfstudio.android.core.mvi.event.Event
import ru.surfstudio.android.core.mvi.event.RequestEvent
import ru.surfstudio.android.core.mvi.event.lifecycle.LifecycleEvent
import ru.surfstudio.android.core.mvp.binding.rx.request.Request
import ru.surfstudio.android.core.ui.state.LifecycleStage

internal sealed class RegistrationPasswordEvent : Event {

    data class Navigation(override var event: NavCommandsEvent = NavCommandsEvent()) :
        NavCommandsComposition, RegistrationPasswordEvent()

    data class Lifecycle(override var stage: LifecycleStage) : RegistrationPasswordEvent(),
        LifecycleEvent

    sealed class Input : RegistrationPasswordEvent() {
        data class ValidateFields(val firstPassword: String, val secondPassword: String) : Input()
        object BackClicked : Input()
        object OpenPolicy : Input()
    }

    data class Register(override val request: Request<Unit>) : RequestEvent<Unit>,
        RegistrationPasswordEvent()
}
