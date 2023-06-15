package ru.rlrent.f_profile

import io.reactivex.Observable
import ru.rlrent.f_profile.ProfileEvent.*
import ru.rlrent.i_auth.AuthInteractor
import ru.rlrent.i_session.LoginState
import ru.rlrent.i_session.SessionChangedInteractor
import ru.rlrent.ui.mvi.navigation.base.NavigationMiddleware
import ru.rlrent.ui.mvi.navigation.extension.removeLast
import ru.rlrent.ui.mvi.navigation.extension.replace
import ru.rlrent.ui.mvi.navigation.extension.show
import ru.rlrent.ui.navigation.routes.AuthFragmentRoute
import ru.rlrent.ui.navigation.routes.ChooseBillAmountDialogRoute
import ru.rlrent.ui.rx_bus_events.DisposeDialogRxBusEvent
import ru.surfstudio.android.core.mvi.impls.ui.middleware.BaseMiddleware
import ru.surfstudio.android.core.mvi.impls.ui.middleware.BaseMiddlewareDependency
import ru.surfstudio.android.dagger.scope.PerScreen
import ru.surfstudio.android.navigation.command.fragment.base.FragmentNavigationCommand
import ru.surfstudio.android.rxbus.RxBus
import javax.inject.Inject

@PerScreen
internal class ProfileMiddleware @Inject constructor(
    private val sh: ProfileScreenStateHolder,
    basePresenterDependency: BaseMiddlewareDependency,
    private val navigationMiddleware: NavigationMiddleware,
    private val sessionChangedInteractor: SessionChangedInteractor,
    private val authInteractor: AuthInteractor,
    private val rxBus: RxBus
) : BaseMiddleware<ProfileEvent>(basePresenterDependency) {

    private val state: ProfileState
        get() = sh.value

    override fun transform(eventStream: Observable<ProfileEvent>): Observable<out ProfileEvent> =
        transformations(eventStream) {
            addAll(
                Navigation::class decomposeTo navigationMiddleware,
                onCreate() eventMap { getUser() },
                Input.BackClicked::class mapTo { navigateToPreviousScreen() },
                Input.Logout::class reactTo ::logout,
                Input.BillClicked::class mapTo { openBillDialog() },
                Input.DeleteAccount::class reactTo ::logout,
                Input.AddEmailBtnClicked::class reactTo ::logout,
                Input.RetryClicked::class eventMapTo { getUser() },
                sessionChangedInteractor.observeSessionChanged()
                    .filter { it == LoginState.NOT_AUTHORIZED }.map {
                        Navigation().replace(
                            route = AuthFragmentRoute(),
                            sourceTag = FragmentNavigationCommand.ACTIVITY_NAVIGATION_TAG
                        )
                    },
                rxBus.observeEvents(DisposeDialogRxBusEvent::class.java).flatMap { getUser() }
            )
        }

    private fun navigateToPreviousScreen(): Navigation {
        return Navigation().removeLast()
    }

    private fun logout(event: Input) {
        sessionChangedInteractor.onForceLogout()
    }

    private fun openBillDialog(): Navigation {
        return Navigation().show(ChooseBillAmountDialogRoute(dialogId = BILL_AMOUNT_CHOOSE_DIALOG))
    }

    private fun getUser(): Observable<out ProfileEvent> {
        return authInteractor.getUser()
            .io()
            .asRequestEvent(::GetUser)
    }

    private companion object {
        const val BILL_AMOUNT_CHOOSE_DIALOG = "BILL_AMOUNT_CHOOSE_DIALOG"
    }
}
