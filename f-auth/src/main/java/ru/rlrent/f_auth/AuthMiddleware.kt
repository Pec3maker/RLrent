package ru.rlrent.f_auth

import io.reactivex.Observable
import ru.rlrent.i_auth.AuthInteractor
import ru.rlrent.ui.mvi.navigation.base.NavigationMiddleware
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
                AuthEvent.Navigation::class decomposeTo navigationMiddleware,
            )
        }

//    private fun signIn(event: AuthEvent.Input.SignIn): Observable<out AuthEvent> {
//        return authInteractor.login(event.login, event.password)
//            .io()
//            .asRequestEvent(::Authorize)
//    }
}
