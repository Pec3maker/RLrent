package ru.rlrent.f_auth.auth

import io.reactivex.Observable
import ru.rlrent.f_auth.auth.AuthEvent.Input
import ru.rlrent.f_auth.auth.AuthEvent.Navigation
import ru.rlrent.i_auth.AuthInteractor
import ru.rlrent.ui.mvi.navigation.base.NavigationMiddleware
import ru.rlrent.ui.mvi.navigation.extension.start
import ru.rlrent.ui.navigation.routes.OpenUrlRoute
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
                Input.OpenPolicy::class mapTo ::openPolicy
            )
        }

    private fun openPolicy(event: Input.OpenPolicy): AuthEvent {
        return Navigation().start(OpenUrlRoute(POLICY_URL))
    }

//    private fun signIn(event: AuthEvent.Input.SignIn): Observable<out AuthEvent> {
//        return authInteractor.login(event.login, event.password)
//            .io()
//            .asRequestEvent(::Authorize)
//    }

    companion object {
        private const val POLICY_URL = "https://www.privacypolicies.com/live/5c6409f1-1964-44bb-8b24-a4ce212b4088"
    }
}
