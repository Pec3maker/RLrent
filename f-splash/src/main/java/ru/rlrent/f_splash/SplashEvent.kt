package ru.rlrent.f_splash

import ru.rlrent.ui.mvi.navigation.event.NavCommandsComposition
import ru.rlrent.ui.mvi.navigation.event.NavCommandsEvent
import ru.surfstudio.android.core.mvi.event.Event
import ru.surfstudio.android.core.mvi.event.lifecycle.LifecycleEvent
import ru.surfstudio.android.core.ui.state.LifecycleStage

/**
 * События сплэш экрана
 */
sealed class SplashEvent : Event {

    data class Lifecycle(override var stage: LifecycleStage) : SplashEvent(), LifecycleEvent
    data class Navigation(override var event: NavCommandsEvent = NavCommandsEvent()) :
        SplashEvent(), NavCommandsComposition

    object PermissionNotGranted : SplashEvent()
}