package ru.rlrent.f_main.tab

import ru.surfstudio.android.core.mvi.event.Event
import ru.surfstudio.android.core.mvi.event.lifecycle.LifecycleEvent
import ru.surfstudio.android.core.ui.state.LifecycleStage
import ru.surfstudio.practice.ui.mvi.navigation.event.NavCommandsComposition
import ru.surfstudio.practice.ui.mvi.navigation.event.NavCommandsEvent

internal sealed class MainTabEvent : Event {

    data class Navigation(override var event: NavCommandsEvent = NavCommandsEvent()) :
        NavCommandsComposition, MainTabEvent()

    data class Lifecycle(override var stage: LifecycleStage) : MainTabEvent(), LifecycleEvent

    sealed class Input : MainTabEvent() {
        object UserInfoClicked : Input()
        data class TabSelected(val position: Int) : Input()
    }

    data class ChangePhvState(val isLoading: Boolean) : MainTabEvent()
}
