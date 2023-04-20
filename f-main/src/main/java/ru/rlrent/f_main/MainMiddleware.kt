package ru.rlrent.f_main

import io.reactivex.Observable
import ru.rlrent.f_main.MainEvent.Navigation
import ru.rlrent.i_session.SessionChangedInteractor
import ru.rlrent.ui.mvi.navigation.base.NavigationMiddleware
import ru.rlrent.ui.mvi.navigation.extension.replace
import ru.rlrent.ui.navigation.routes.AuthFragmentRoute
import ru.rlrent.ui.navigation.routes.MapFragmentRoute
import ru.surfstudio.android.core.mvi.impls.ui.middleware.BaseMiddleware
import ru.surfstudio.android.core.mvi.impls.ui.middleware.BaseMiddlewareDependency
import ru.surfstudio.android.dagger.scope.PerScreen
import javax.inject.Inject

@PerScreen
internal class MainMiddleware @Inject constructor(
    basePresenterDependency: BaseMiddlewareDependency,
    private val navigationMiddleware: NavigationMiddleware,
    private val sessionChangedInteractor: SessionChangedInteractor
) : BaseMiddleware<MainEvent>(basePresenterDependency) {

    override fun transform(eventStream: Observable<MainEvent>): Observable<out MainEvent> =
        transformations(eventStream) {
            addAll(
                Navigation::class decomposeTo navigationMiddleware,
                onCreate() map { navigate() }
            )
        }

    private fun navigate(): Navigation {
        return if (sessionChangedInteractor.isLoggedIn) {
            Navigation().replace(MapFragmentRoute())
        } else {
            Navigation().replace(AuthFragmentRoute())
        }
    }
}
