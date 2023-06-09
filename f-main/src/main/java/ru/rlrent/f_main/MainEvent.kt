package ru.rlrent.f_main

import ru.rlrent.ui.mvi.navigation.event.NavCommandsComposition
import ru.rlrent.ui.mvi.navigation.event.NavCommandsEvent
import ru.surfstudio.android.core.mvi.event.Event
import ru.surfstudio.android.core.mvi.event.lifecycle.LifecycleEvent
import ru.surfstudio.android.core.ui.state.LifecycleStage

internal sealed class MainEvent : Event {

    data class Navigation(override var event: NavCommandsEvent = NavCommandsEvent()) :
        NavCommandsComposition, MainEvent()

    data class Lifecycle(override var stage: LifecycleStage) : MainEvent(), LifecycleEvent
}
