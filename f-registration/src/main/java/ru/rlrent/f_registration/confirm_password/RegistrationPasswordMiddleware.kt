package ru.rlrent.f_registration.confirm_password

import io.reactivex.Observable
import ru.android.rlrent.f_registration.BuildConfig
import ru.rlrent.f_registration.confirm_password.RegistrationPasswordEvent.*
import ru.rlrent.i_auth.AuthInteractor
import ru.rlrent.ui.mvi.navigation.base.NavigationMiddleware
import ru.rlrent.ui.mvi.navigation.extension.removeLast
import ru.rlrent.ui.mvi.navigation.extension.removeUntil
import ru.rlrent.ui.mvi.navigation.extension.start
import ru.rlrent.ui.navigation.routes.AuthFragmentRoute
import ru.rlrent.ui.navigation.routes.OpenUrlRoute
import ru.rlrent.ui.navigation.routes.RegistrationPasswordFragmentRoute
import ru.surfstudio.android.core.mvi.impls.ui.middleware.BaseMiddleware
import ru.surfstudio.android.core.mvi.impls.ui.middleware.BaseMiddlewareDependency
import ru.surfstudio.android.dagger.scope.PerScreen
import javax.inject.Inject

@PerScreen
internal class RegistrationPasswordMiddleware @Inject constructor(
    basePresenterDependency: BaseMiddlewareDependency,
    private val sh: RegistrationPasswordScreenStateHolder,
    private val authInteractor: AuthInteractor,
    private val route: RegistrationPasswordFragmentRoute,
    private val navigationMiddleware: NavigationMiddleware
) : BaseMiddleware<RegistrationPasswordEvent>(basePresenterDependency) {

    private val state: RegistrationPasswordState
        get() = sh.value

    override fun transform(eventStream: Observable<RegistrationPasswordEvent>): Observable<out RegistrationPasswordEvent> =
        transformations(eventStream) {
            addAll(
                Navigation::class decomposeTo navigationMiddleware,
                Input.ValidateFields::class.filter { state.isDataValid }.eventMap { register(it) },
                Input.BackClicked::class mapTo { navigateToPreviousScreen() },
                Input.OpenPolicy::class mapTo ::openPolicy,
                Register::class.filter { it.isSuccess }.map { onRegistrationSuccess() }
            )
        }

    private fun register(event: Input.ValidateFields): Observable<out RegistrationPasswordEvent> {
        return authInteractor.register(
            login = route.login,
            phone = route.phone,
            password = event.firstPassword
        ).io().asRequestEvent(::Register)
    }

    private fun navigateToPreviousScreen(): Navigation {
        return Navigation().removeLast()
    }

    private fun openPolicy(event: Input.OpenPolicy): Navigation {
        return Navigation().start(OpenUrlRoute(BuildConfig.POLICY_URL))
    }

    private fun onRegistrationSuccess(): Navigation {
        return Navigation().removeUntil(AuthFragmentRoute(), isInclusive = false)
    }
}
