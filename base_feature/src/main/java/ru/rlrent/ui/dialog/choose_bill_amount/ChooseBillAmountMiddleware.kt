package ru.rlrent.ui.dialog.choose_bill_amount

import io.reactivex.Observable
import ru.rlrent.i_auth.AuthInteractor
import ru.rlrent.ui.dialog.choose_bill_amount.ChooseBillAmountEvent.*
import ru.rlrent.ui.mvi.navigation.base.NavigationMiddleware
import ru.rlrent.ui.mvi.navigation.extension.dismiss
import ru.rlrent.ui.navigation.routes.ChooseBillAmountDialogRoute
import ru.rlrent.ui.rx_bus_events.DisposeDialogRxBusEvent
import ru.surfstudio.android.core.mvi.impls.ui.middleware.BaseMiddleware
import ru.surfstudio.android.core.mvi.impls.ui.middleware.BaseMiddlewareDependency
import ru.surfstudio.android.dagger.scope.PerScreen
import ru.surfstudio.android.rx.extension.toObservable
import ru.surfstudio.android.rxbus.RxBus
import javax.inject.Inject

@PerScreen
internal class ChooseBillAmountMiddleware @Inject constructor(
    basePresenterDependency: BaseMiddlewareDependency,
    private val navigationMiddleware: NavigationMiddleware,
    private val sh: ChooseBillAmountStateHolder,
    private val rxBus: RxBus,
    private val route: ChooseBillAmountDialogRoute,
    private val ch: ChooseBillAmountCommandHolder,
    private val authInteractor: AuthInteractor
) : BaseMiddleware<ChooseBillAmountEvent>(basePresenterDependency) {

    private val state: ChooseBillAmountState get() = sh.value

    override fun transform(eventStream: Observable<ChooseBillAmountEvent>): Observable<out ChooseBillAmountEvent> =
        transformations(eventStream) {
            addAll(
                onDestroy() react ::handleOnDestroy,
                Navigation::class decomposeTo navigationMiddleware,
                Input.SetBillAmountClicked::class reactTo ::handleBillAmountClicked,
                Input.MakePayment::class eventMapTo { makePayment(it.token) }
            )
        }

    private fun handleOnDestroy(event: ChooseBillAmountEvent) {
        rxBus.emitEvent(DisposeDialogRxBusEvent)
    }

    private fun handleBillAmountClicked(event: Input.SetBillAmountClicked) {
        val billAmountValue = state.displayValues[event.chosenTimeIndex].toInt()
        ch.openPaymentDialog.accept(billAmountValue.toDouble())
    }

    private fun makePayment(token: String): Observable<out ChooseBillAmountEvent> {
        return merge(
            authInteractor.makePayment(
                paymentToken = token,
                amount = state.displayValues[state.chosenTimeIndex].toFloat()
            )
                .io()
                .asRequestEvent(::RequestPayment)
                .doOnComplete { dismiss() }
        )
    }

    private fun dismiss(): Observable<out ChooseBillAmountEvent> {
        rxBus.emitEvent(DisposeDialogRxBusEvent)
        return Navigation().dismiss(
            route = route
        ).toObservable()
    }
}
