package ru.rlrent.f_profile

import ru.rlrent.domain.user.User
import ru.rlrent.ui.mvi.navigation.event.NavCommandsComposition
import ru.rlrent.ui.mvi.navigation.event.NavCommandsEvent
import ru.surfstudio.android.core.mvi.event.Event
import ru.surfstudio.android.core.mvi.event.RequestEvent
import ru.surfstudio.android.core.mvi.event.lifecycle.LifecycleEvent
import ru.surfstudio.android.core.mvp.binding.rx.request.Request
import ru.surfstudio.android.core.ui.state.LifecycleStage

internal sealed class ProfileEvent : Event {

    data class Navigation(override var event: NavCommandsEvent = NavCommandsEvent()) :
        NavCommandsComposition, ProfileEvent()

    data class Lifecycle(override var stage: LifecycleStage) : ProfileEvent(), LifecycleEvent

    sealed class Input : ProfileEvent() {
        object BackClicked : Input()

        object RetryClicked : Input()
        object Logout : Input()
        object DeleteAccount : Input()
        object BillClicked : Input()
        object AddEmailBtnClicked : Input()
    }

    data class GetUser(override val request: Request<User>) : RequestEvent<User>, ProfileEvent()
}
