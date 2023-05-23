package ru.rlrent.f_profile

import ru.rlrent.ui.mvi.navigation.event.NavCommandsComposition
import ru.rlrent.ui.mvi.navigation.event.NavCommandsEvent
import ru.surfstudio.android.core.mvi.event.Event
import ru.surfstudio.android.core.mvi.event.lifecycle.LifecycleEvent
import ru.surfstudio.android.core.ui.state.LifecycleStage

internal sealed class ProfileEvent : Event {

    data class Navigation(override var event: NavCommandsEvent = NavCommandsEvent()) :
        NavCommandsComposition, ProfileEvent()

    data class Lifecycle(override var stage: LifecycleStage) : ProfileEvent(), LifecycleEvent

    sealed class Input : ProfileEvent() {
        object BackClicked : Input()
        object Logout : Input()
        object DeleteAccount : Input()
    }
}
