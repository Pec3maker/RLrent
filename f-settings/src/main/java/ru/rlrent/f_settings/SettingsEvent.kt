package ru.rlrent.f_settings

import ru.rlrent.ui.mvi.navigation.event.NavCommandsComposition
import ru.rlrent.ui.mvi.navigation.event.NavCommandsEvent
import ru.surfstudio.android.core.mvi.event.Event
import ru.surfstudio.android.core.mvi.event.lifecycle.LifecycleEvent
import ru.surfstudio.android.core.ui.state.LifecycleStage

internal sealed class SettingsEvent : Event {

    data class Navigation(override var event: NavCommandsEvent = NavCommandsEvent()) :
        NavCommandsComposition, SettingsEvent()

    data class Lifecycle(override var stage: LifecycleStage) : SettingsEvent(), LifecycleEvent

    sealed class Input : SettingsEvent() {
        object SetDarkTheme : Input()
        object SetLightTheme : Input()
        object BackClicked : Input()
    }
}
