package ru.rlrent.f_map

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import ru.android.rlrent.f_map.R
import ru.rlrent.domain.transport.Transport
import ru.rlrent.domain.transport.TransportType
import ru.rlrent.domain.transport.Zone
import ru.rlrent.domain.user.User
import ru.rlrent.f_map.MapEvent.*
import ru.rlrent.ui.mvi.mappers.RequestMappers
import ru.rlrent.ui.mvi.placeholder.LoadStateType
import ru.rlrent.ui.util.placeholderState
import ru.surfstudio.android.core.mvi.impls.ui.reactor.BaseReactorDependency
import ru.surfstudio.android.core.mvi.impls.ui.reducer.BaseReducer
import ru.surfstudio.android.core.mvi.ui.mapper.RequestMapper
import ru.surfstudio.android.core.mvp.binding.rx.relation.mvp.Command
import ru.surfstudio.android.core.mvp.binding.rx.relation.mvp.State
import ru.surfstudio.android.dagger.scope.PerScreen
import javax.inject.Inject

/**
 * State [MapFragmentView]
 */
data class MapState(
    val availableTransport: List<Transport> = listOf(),
    val availableZones: List<Zone> = listOf(),
    val placeholderState: LoadStateType = LoadStateType.None(),
    val user: User = User(),
    val tripStarted: Boolean = false,
    @DrawableRes val mapTransport: Int = R.drawable.scooter_crop,
    val isBottomBarVisible: Boolean = false,
    val currentTransport: Transport = Transport(),
    val currentTripId: Int = 0
)

/**
 * State Holder [MapFragmentView]
 */
@PerScreen
internal class MapScreenStateHolder @Inject constructor() : State<MapState>(MapState())

/**
 * Command Holder [MapFragmentView]
 */
@PerScreen
internal class MapCommandHolder @Inject constructor() {
    val showMessage = Command<@StringRes Int>()
}

/**
 * Reducer [MapFragmentView]
 */
@PerScreen
internal class MapReducer @Inject constructor(
    dependency: BaseReactorDependency,
    private val ch: MapCommandHolder
) : BaseReducer<MapEvent, MapState>(dependency) {

    override fun reduce(state: MapState, event: MapEvent): MapState {
        return when (event) {
            is GetUser -> handleGetUserRequest(state, event)
            is GetTransport -> handleGetTransportRequest(state, event)
            is GetZones -> handleGetZonesRequest(state, event)
            is StartTrip -> handleStartTripRequest(state, event)
            is FinishTrip -> handleFinishTripRequest(state, event)
            is Input.TransportClicked -> handleTransportClicked(state, event)
            is Input.ScreenTouched -> state.copy(isBottomBarVisible = false)
            else -> state
        }
    }

    private fun handleStartTripRequest(state: MapState, event: StartTrip): MapState {
        val tripRequest = RequestMapper.builder(event.request)
            .mapData(RequestMappers.data.default())
            .mapLoading(RequestMappers.loading.default())
            .handleError(RequestMappers.error.loadingBased(errorHandler))
            .reactOnSuccess { _ ->
                ch.showMessage.accept(R.string.start_trip_text)
            }
            .reactOnError { _ ->
                ch.showMessage.accept(R.string.error_start_trip_text)
            }
            .build()

        return state.copy(
            tripStarted = tripRequest.hasData,
            currentTripId = tripRequest.data?.id ?: 0,
        )
    }

    private fun handleFinishTripRequest(state: MapState, event: FinishTrip): MapState {
        val tripRequest = RequestMapper.builder(event.request)
            .mapData(RequestMappers.data.default())
            .mapLoading(RequestMappers.loading.default())
            .handleError(RequestMappers.error.loadingBased(errorHandler))
            .reactOnSuccess { _ ->
                ch.showMessage.accept(R.string.end_trip_text)
            }
            .reactOnError { _ ->
                ch.showMessage.accept(R.string.error_end_trip_text)
            }
            .build()

        return state.copy(
            tripStarted = !tripRequest.hasData,
            currentTripId = tripRequest.data?.id ?: 0,
            isBottomBarVisible = false
        )
    }

    private fun handleTransportClicked(state: MapState, event: Input.TransportClicked): MapState {
        return if (!state.tripStarted) {
            state.copy(
                mapTransport = if (event.transport.transportType == TransportType.ELECTRIC_SCOOTER) {
                    R.drawable.scooter_crop
                } else {
                    R.drawable.bike_crop
                },
                isBottomBarVisible = true,
                currentTransport = event.transport
            )
        } else {
            state
        }
    }

    private fun handleGetZonesRequest(state: MapState, event: GetZones): MapState {
        val zonesRequest = RequestMapper.builder(event.request)
            .mapData(RequestMappers.data.default())
            .mapLoading(RequestMappers.loading.default())
            .handleError(RequestMappers.error.loadingBased(errorHandler))
            .build()

        return state.copy(
            placeholderState = zonesRequest.placeholderState,
            availableZones = zonesRequest.data ?: emptyList()
        )
    }

    private fun handleGetUserRequest(state: MapState, event: GetUser): MapState {
        val userRequest = RequestMapper.builder(event.request)
            .mapData(RequestMappers.data.default())
            .mapLoading(RequestMappers.loading.default())
            .handleError(RequestMappers.error.loadingBased(errorHandler))
            .build()

        return state.copy(
            placeholderState = userRequest.placeholderState,
            user = userRequest.data ?: User()
        )
    }

    private fun handleGetTransportRequest(state: MapState, event: GetTransport): MapState {
        val transportRequest = RequestMapper.builder(event.request)
            .mapData(RequestMappers.data.default())
            .mapLoading(RequestMappers.loading.default())
            .handleError(RequestMappers.error.loadingBased(errorHandler))
            .build()

        return state.copy(
            placeholderState = transportRequest.placeholderState,
            availableTransport = transportRequest.data ?: emptyList()
        )
    }
}
