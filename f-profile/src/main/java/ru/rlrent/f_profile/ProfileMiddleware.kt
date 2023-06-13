package ru.rlrent.f_profile

import io.reactivex.Observable
import ru.rlrent.f_profile.ProfileEvent.Input
import ru.rlrent.f_profile.ProfileEvent.Navigation
import ru.rlrent.i_session.LoginState
import ru.rlrent.i_session.SessionChangedInteractor
import ru.rlrent.ui.mvi.navigation.base.NavigationMiddleware
import ru.rlrent.ui.mvi.navigation.extension.removeLast
import ru.rlrent.ui.mvi.navigation.extension.replace
import ru.rlrent.ui.navigation.routes.AuthFragmentRoute
import ru.surfstudio.android.core.mvi.impls.ui.middleware.BaseMiddleware
import ru.surfstudio.android.core.mvi.impls.ui.middleware.BaseMiddlewareDependency
import ru.surfstudio.android.dagger.scope.PerScreen
import ru.surfstudio.android.navigation.command.fragment.base.FragmentNavigationCommand
import javax.inject.Inject

@PerScreen
internal class ProfileMiddleware @Inject constructor(
    private val sh: ProfileScreenStateHolder,
    basePresenterDependency: BaseMiddlewareDependency,
    private val navigationMiddleware: NavigationMiddleware,
    private val sessionChangedInteractor: SessionChangedInteractor,
) : BaseMiddleware<ProfileEvent>(basePresenterDependency) {

    private val state: ProfileState
        get() = sh.value

    override fun transform(eventStream: Observable<ProfileEvent>): Observable<out ProfileEvent> =
        transformations(eventStream) {
            addAll(
                Navigation::class decomposeTo navigationMiddleware,
                Input.BackClicked::class mapTo { navigateToPreviousScreen() },
                Input.Logout::class reactTo ::logout,
                sessionChangedInteractor.observeSessionChanged()
                    .filter { it == LoginState.NOT_AUTHORIZED }.map {
                        Navigation().replace(
                            route = AuthFragmentRoute(),
                            sourceTag = FragmentNavigationCommand.ACTIVITY_NAVIGATION_TAG
                        )
                    }
            )
        }

    private fun navigateToPreviousScreen(): Navigation {
        return Navigation().removeLast()
    }

    private fun logout(event: Input.Logout) {
        sessionChangedInteractor.onForceLogout()
    }
}
