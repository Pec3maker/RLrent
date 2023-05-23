package ru.rlrent.f_splash

import io.reactivex.Completable
import io.reactivex.Observable
import ru.android.rlrent.f_splash.R
import ru.rlrent.f_splash.SplashEvent.Navigation
import ru.rlrent.f_splash.SplashEvent.PermissionNotGranted
import ru.rlrent.i_initialization.InitializeAppInteractor
import ru.rlrent.i_storage.theme.ThemeChangedInteractor
import ru.rlrent.ui.mvi.navigation.base.NavigationMiddleware
import ru.rlrent.ui.mvi.navigation.extension.replace
import ru.rlrent.ui.navigation.routes.MainActivityRoute
import ru.rlrent.ui.permissions.AppPermissionManager
import ru.rlrent.ui.permissions.LocationPermissionRequest
import ru.surfstudio.android.core.mvi.impls.ui.middleware.BaseMiddleware
import ru.surfstudio.android.core.mvi.impls.ui.middleware.BaseMiddlewareDependency
import ru.surfstudio.android.core.ui.provider.resource.ResourceProvider
import ru.surfstudio.android.dagger.scope.PerScreen
import ru.surfstudio.android.rx.extension.toObservable
import ru.surfstudio.android.utilktx.ktx.text.EMPTY_STRING
import ru.surfstudio.android.utilktx.util.SdkUtils
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Минимальное время в миллисекундах, в течение которого показывается сплэш
 */
const val TRANSITION_DELAY_MS = 800L

/**
 * Middleware сплэш экрана [SplashActivityView].
 */
@PerScreen
class SplashMiddleware @Inject constructor(
    baseMiddlewareDependency: BaseMiddlewareDependency,
    private val navigationMiddleware: NavigationMiddleware,
    private val initializeAppInteractor: InitializeAppInteractor,
    private val permissionManager: AppPermissionManager,
    private val resourceProvider: ResourceProvider,
    private val themeChangedInteractor: ThemeChangedInteractor
) : BaseMiddleware<SplashEvent>(baseMiddlewareDependency) {

    override fun transform(eventStream: Observable<SplashEvent>) =
        transformations(eventStream) {
            addAll(
                onCreate() eventMap {
                    themeChangedInteractor.activateSavedTheme()
                    handlePermission()
                },
                Navigation::class decomposeTo navigationMiddleware,
                PermissionNotGranted::class eventMapTo { handlePermission() }
            )
        }

    private fun handlePermission(): Observable<out SplashEvent> {
        return permissionManager.request(
            LocationPermissionRequest(
                settingsRationalString = resourceProvider.getString(R.string.splash_location_settings_rational_text)
            )
        )
            .toObservable()
            .flatMap { isGranted ->
                if (isGranted) {
                    mergeInitDelay()
                } else {
                    PermissionNotGranted.toObservable()
                }
            }
    }

    private fun mergeInitDelay(): Observable<out SplashEvent> {
        val transitionDelay = if (SdkUtils.isAtLeastS()) {
            TRANSITION_DELAY_MS / 4
        } else {
            TRANSITION_DELAY_MS
        }
        val delay = Completable.timer(transitionDelay, TimeUnit.MILLISECONDS)
        val worker = initializeAppInteractor.initialize()
        return Completable.merge(arrayListOf(delay, worker))
            .io()
            .toSingleDefault(EMPTY_STRING)
            .toObservable()
            .map { openNextScreen() }
    }

    private fun openNextScreen(): SplashEvent {
        return Navigation().replace(MainActivityRoute())
    }
}