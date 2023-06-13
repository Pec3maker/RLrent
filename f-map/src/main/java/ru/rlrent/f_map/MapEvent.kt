package ru.rlrent.f_map

import ru.rlrent.domain.transport.Transport
import ru.rlrent.domain.transport.Zone
import ru.rlrent.domain.trip.Trip
import ru.rlrent.domain.user.User
import ru.rlrent.ui.mvi.navigation.event.NavCommandsComposition
import ru.rlrent.ui.mvi.navigation.event.NavCommandsEvent
import ru.surfstudio.android.core.mvi.event.Event
import ru.surfstudio.android.core.mvi.event.RequestEvent
import ru.surfstudio.android.core.mvi.event.lifecycle.LifecycleEvent
import ru.surfstudio.android.core.mvp.binding.rx.request.Request
import ru.surfstudio.android.core.ui.state.LifecycleStage

internal sealed class MapEvent : Event {

    data class Navigation(override var event: NavCommandsEvent = NavCommandsEvent()) :
        NavCommandsComposition, MapEvent()

    data class Lifecycle(override var stage: LifecycleStage) : MapEvent(), LifecycleEvent

    sealed class Input : MapEvent() {
        object ProfileClicked : Input()
        object InfoClicked : Input()
        object SettingsClicked : Input()
        object RetryClicked : Input()
        data class TransportClicked(val transport: Transport) : Input()
        object ScreenTouched : Input()
        object GoBtnClicked : Input()
    }

    data class GetUser(override val request: Request<User>) : RequestEvent<User>, MapEvent()

    data class GetTransport(override val request: Request<List<Transport>>) :
        RequestEvent<List<Transport>>, MapEvent()

    data class GetZones(override val request: Request<List<Zone>>) : RequestEvent<List<Zone>>,
        MapEvent()

    data class StartTrip(override val request: Request<Trip>) : RequestEvent<Trip>, MapEvent()
    data class FinishTrip(override val request: Request<Trip>) : RequestEvent<Trip>, MapEvent()
}
