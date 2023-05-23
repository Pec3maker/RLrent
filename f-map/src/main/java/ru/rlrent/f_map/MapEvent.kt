package ru.rlrent.f_map

import ru.rlrent.ui.mvi.navigation.event.NavCommandsComposition
import ru.rlrent.ui.mvi.navigation.event.NavCommandsEvent
import ru.surfstudio.android.core.mvi.event.Event
import ru.surfstudio.android.core.mvi.event.lifecycle.LifecycleEvent
import ru.surfstudio.android.core.ui.state.LifecycleStage

internal sealed class MapEvent : Event {

    data class Navigation(override var event: NavCommandsEvent = NavCommandsEvent()) :
        NavCommandsComposition, MapEvent()

    data class Lifecycle(override var stage: LifecycleStage) : MapEvent(), LifecycleEvent

    sealed class Input : MapEvent() {
        object ProfileClicked : Input()
        object InfoClicked : Input()
        object SettingsClicked : Input()
        data class UpdateUserLocation(val lat: Double, val long: Double) : Input()
    }
}
