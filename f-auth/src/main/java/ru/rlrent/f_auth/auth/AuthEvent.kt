package ru.rlrent.f_auth.auth

import ru.rlrent.domain.auth.LoginInfo
import ru.rlrent.ui.mvi.navigation.event.NavCommandsComposition
import ru.rlrent.ui.mvi.navigation.event.NavCommandsEvent
import ru.surfstudio.android.core.mvi.event.Event
import ru.surfstudio.android.core.mvi.event.RequestEvent
import ru.surfstudio.android.core.mvi.event.lifecycle.LifecycleEvent
import ru.surfstudio.android.core.mvp.binding.rx.request.Request
import ru.surfstudio.android.core.ui.state.LifecycleStage

internal sealed class AuthEvent : Event {

    data class Navigation(override var event: NavCommandsEvent = NavCommandsEvent()) :
        NavCommandsComposition, AuthEvent()

    data class Lifecycle(override var stage: LifecycleStage) : AuthEvent(), LifecycleEvent

    sealed class Input : AuthEvent() {
        data class SignIn(val login: String, val password: String) : Input()
        object OpenPolicy : Input()
        object OpenRegistrationScreen : Input()
    }

    data class Authorize(override val request: Request<LoginInfo>) : RequestEvent<LoginInfo>,
        AuthEvent()
}
