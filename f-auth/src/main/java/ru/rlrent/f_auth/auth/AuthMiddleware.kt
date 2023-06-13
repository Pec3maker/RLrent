package ru.rlrent.f_auth.auth

import io.reactivex.Observable
import ru.android.rlrent.f_auth.BuildConfig
import ru.rlrent.f_auth.auth.AuthEvent.*
import ru.rlrent.i_auth.AuthInteractor
import ru.rlrent.ui.mvi.navigation.base.NavigationMiddleware
import ru.rlrent.ui.mvi.navigation.extension.replace
import ru.rlrent.ui.mvi.navigation.extension.start
import ru.rlrent.ui.navigation.routes.MapFragmentRoute
import ru.rlrent.ui.navigation.routes.OpenUrlRoute
import ru.rlrent.ui.navigation.routes.RegistrationNameFragmentRoute
import ru.surfstudio.android.core.mvi.impls.ui.middleware.BaseMiddleware
import ru.surfstudio.android.core.mvi.impls.ui.middleware.BaseMiddlewareDependency
import ru.surfstudio.android.dagger.scope.PerScreen
import javax.inject.Inject

@PerScreen
internal class AuthMiddleware @Inject constructor(
    private val sh: AuthScreenStateHolder,
    private val authInteractor: AuthInteractor,
    basePresenterDependency: BaseMiddlewareDependency,
    private val navigationMiddleware: NavigationMiddleware
) : BaseMiddleware<AuthEvent>(basePresenterDependency) {

    private val state: AuthState
        get() = sh.value

    override fun transform(eventStream: Observable<AuthEvent>): Observable<out AuthEvent> =
        transformations(eventStream) {
            addAll(
                Navigation::class decomposeTo navigationMiddleware,
                Input.SignIn::class.filter { state.isDataValid }.flatMap { signIn(it) },
                Authorize::class.filter { it.isSuccess }.map { onAuthorizationSuccess() },
                Input.OpenPolicy::class mapTo ::openPolicy,
                Input.OpenRegistrationScreen::class mapTo ::openRegistrationScreen
            )
        }

    private fun openRegistrationScreen(event: Input.OpenRegistrationScreen): AuthEvent {
        return Navigation().replace(RegistrationNameFragmentRoute())
    }

    private fun signIn(event: Input.SignIn): Observable<out AuthEvent> {
        return authInteractor.login(event.login, event.password)
            .io()
            .asRequestEvent(::Authorize)
    }

    private fun onAuthorizationSuccess(): Navigation {
        return Navigation().replace(MapFragmentRoute())
    }

    private fun openPolicy(event: Input.OpenPolicy): AuthEvent {
        return Navigation().start(OpenUrlRoute(BuildConfig.POLICY_URL))
    }
}
