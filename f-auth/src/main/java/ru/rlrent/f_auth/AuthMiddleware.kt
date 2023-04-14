package ru.rlrent.f_auth

import io.reactivex.Observable
import ru.surfstudio.android.core.mvi.impls.ui.middleware.BaseMiddleware
import ru.surfstudio.android.core.mvi.impls.ui.middleware.BaseMiddlewareDependency
import ru.surfstudio.android.dagger.scope.PerScreen
import ru.surfstudio.practice.f_auth.AuthEvent.Authorize
import ru.surfstudio.practice.f_auth.AuthEvent.Navigation
import ru.surfstudio.practice.i_auth.AuthInteractor
import ru.surfstudio.practice.ui.mvi.navigation.base.NavigationMiddleware
import ru.surfstudio.practice.ui.mvi.navigation.extension.replace
import ru.surfstudio.practice.ui.navigation.routes.MainTabRoute
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
                AuthEvent.Input.SignIn::class.filter { state.isDataValid }.flatMap { signIn(it) },
                Authorize::class.filter { it.isSuccess }.map { onAuthorizationSuccess() }
            )
        }

    private fun signIn(event: AuthEvent.Input.SignIn): Observable<out AuthEvent> {
        return authInteractor.login(event.login, event.password)
            .io()
            .asRequestEvent(::Authorize)
    }

    private fun onAuthorizationSuccess(): Navigation {
        return Navigation().replace(MainTabRoute())
    }
}
