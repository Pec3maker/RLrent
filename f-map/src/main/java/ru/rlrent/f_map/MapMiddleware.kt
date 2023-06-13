package ru.rlrent.f_map

import io.reactivex.Observable
import ru.android.rlrent.f_map.BuildConfig
import ru.rlrent.f_map.MapEvent.*
import ru.rlrent.i_auth.AuthInteractor
import ru.rlrent.i_transport.TransportInteractor
import ru.rlrent.i_trip.TripInteractor
import ru.rlrent.ui.mvi.navigation.base.NavigationMiddleware
import ru.rlrent.ui.mvi.navigation.extension.replace
import ru.rlrent.ui.mvi.navigation.extension.start
import ru.rlrent.ui.navigation.routes.OpenUrlRoute
import ru.rlrent.ui.navigation.routes.ProfileFragmentRoute
import ru.rlrent.ui.navigation.routes.SettingsFragmentRoute
import ru.surfstudio.android.core.mvi.impls.ui.middleware.BaseMiddleware
import ru.surfstudio.android.core.mvi.impls.ui.middleware.BaseMiddlewareDependency
import ru.surfstudio.android.dagger.scope.PerScreen
import ru.surfstudio.android.navigation.command.fragment.base.FragmentNavigationCommand
import javax.inject.Inject

@PerScreen
internal class MapMiddleware @Inject constructor(
    private val sh: MapScreenStateHolder,
    basePresenterDependency: BaseMiddlewareDependency,
    private val navigationMiddleware: NavigationMiddleware,
    private val authInteractor: AuthInteractor,
    private val transportInteractor: TransportInteractor,
    private val tripInteractor: TripInteractor
) : BaseMiddleware<MapEvent>(basePresenterDependency) {

    private val state: MapState
        get() = sh.value

    override fun transform(eventStream: Observable<MapEvent>): Observable<out MapEvent> =
        transformations(eventStream) {
            addAll(
                Navigation::class decomposeTo navigationMiddleware,
                onResume() eventMap ::handleOnCreate,
                Input.SettingsClicked::class mapTo ::navigateToSettings,
                Input.InfoClicked::class mapTo ::navigateToInfo,
                Input.ProfileClicked::class mapTo ::navigateToProfile,
                Input.RetryClicked::class eventMapTo {
                    merge(
                        getUser(),
                        getTransport(),
                        getParkZones()
                    )
                },
                Input.GoBtnClicked::class.filter { !state.tripStarted }.eventMap { startTrip() },
                Input.GoBtnClicked::class.filter { state.tripStarted }.eventMap { finishTrip() }
            )
        }

    private fun finishTrip(): Observable<out MapEvent> {
        return tripInteractor.finishTrip()
            .io()
            .asRequestEvent(::FinishTrip)
    }

    private fun startTrip(): Observable<out MapEvent> {
        return tripInteractor.startTrip(state.currentTransport.id)
            .io()
            .asRequestEvent(::StartTrip)
    }

    private fun handleOnCreate(event: MapEvent): Observable<out MapEvent> {
        return merge(
            getUser(),
            getTransport(),
            getParkZones()
        )
    }

    private fun getParkZones(): Observable<out MapEvent> {
        return transportInteractor.getZonesList()
            .io()
            .asRequestEvent(::GetZones)
    }

    private fun getTransport(): Observable<out MapEvent> {
        return transportInteractor.getTransportList()
            .io()
            .asRequestEvent(::GetTransport)
    }

    private fun navigateToProfile(event: Input.ProfileClicked): MapEvent {
        return Navigation().replace(
            ProfileFragmentRoute(user = state.user),
            sourceTag = FragmentNavigationCommand.ACTIVITY_NAVIGATION_TAG
        )
    }

    private fun navigateToSettings(event: Input.SettingsClicked): MapEvent {
        return Navigation().replace(
            SettingsFragmentRoute(),
            sourceTag = FragmentNavigationCommand.ACTIVITY_NAVIGATION_TAG
        )
    }

    private fun navigateToInfo(event: Input.InfoClicked): MapEvent {
        return Navigation().start(OpenUrlRoute(BuildConfig.POLICY_URL))
    }

    private fun getUser(): Observable<out MapEvent> {
        return authInteractor.getUser()
            .io()
            .asRequestEvent(::GetUser)
    }
}
