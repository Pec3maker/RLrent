package ru.rlrent.f_registration.name

import ru.rlrent.ui.mvi.navigation.event.NavCommandsComposition
import ru.rlrent.ui.mvi.navigation.event.NavCommandsEvent
import ru.surfstudio.android.core.mvi.event.Event
import ru.surfstudio.android.core.mvi.event.lifecycle.LifecycleEvent
import ru.surfstudio.android.core.ui.state.LifecycleStage

internal sealed class RegistrationNameEvent : Event {

    data class Navigation(override var event: NavCommandsEvent = NavCommandsEvent()) :
        NavCommandsComposition, RegistrationNameEvent()

    data class Lifecycle(override var stage: LifecycleStage) : RegistrationNameEvent(),
        LifecycleEvent

    sealed class Input : RegistrationNameEvent() {
        data class ValidateFields(val name: String, val number: String) : Input()
        object BackClicked : Input()
        object OpenPolicy : Input()
    }
}
