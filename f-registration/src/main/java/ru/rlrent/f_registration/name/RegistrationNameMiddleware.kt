package ru.rlrent.f_registration.name

import io.reactivex.Observable
import ru.android.rlrent.f_registration.BuildConfig
import ru.rlrent.f_registration.name.RegistrationNameEvent.Input
import ru.rlrent.f_registration.name.RegistrationNameEvent.Navigation
import ru.rlrent.ui.mvi.navigation.base.NavigationMiddleware
import ru.rlrent.ui.mvi.navigation.extension.removeLast
import ru.rlrent.ui.mvi.navigation.extension.replace
import ru.rlrent.ui.mvi.navigation.extension.start
import ru.rlrent.ui.navigation.routes.OpenUrlRoute
import ru.rlrent.ui.navigation.routes.RegistrationPasswordFragmentRoute
import ru.surfstudio.android.core.mvi.impls.ui.middleware.BaseMiddleware
import ru.surfstudio.android.core.mvi.impls.ui.middleware.BaseMiddlewareDependency
import ru.surfstudio.android.dagger.scope.PerScreen
import javax.inject.Inject

@PerScreen
internal class RegistrationNameMiddleware @Inject constructor(
    private val sh: RegistrationNameScreenStateHolder,
    basePresenterDependency: BaseMiddlewareDependency,
    private val navigationMiddleware: NavigationMiddleware
) : BaseMiddleware<RegistrationNameEvent>(basePresenterDependency) {

    private val state: RegistrationNameState
        get() = sh.value

    override fun transform(eventStream: Observable<RegistrationNameEvent>): Observable<out RegistrationNameEvent> =
        transformations(eventStream) {
            addAll(
                Navigation::class decomposeTo navigationMiddleware,
                Input.ValidateFields::class.filter { state.isDataValid }.map { openNextScreen(it) },
                Input.BackClicked::class mapTo { navigateToPreviousScreen() },
                Input.OpenPolicy::class mapTo ::openPolicy
            )
        }

    private fun openNextScreen(event: Input.ValidateFields): Navigation {
        return Navigation().replace(
            RegistrationPasswordFragmentRoute(
                login = event.name,
                phone = event.number.filter { it.isDigit() }
            )
        )
    }

    private fun navigateToPreviousScreen(): Navigation {
        return Navigation().removeLast()
    }

    private fun openPolicy(event: Input.OpenPolicy): Navigation {
        return Navigation().start(OpenUrlRoute(BuildConfig.POLICY_URL))
    }
}
