package ru.rlrent.ui.dialog.choose_bill_amount

import androidx.annotation.StringRes
import ru.android.rlrent.base_feature.R
import ru.rlrent.ui.dialog.choose_bill_amount.BillAmount.PayInterval
import ru.rlrent.ui.mvi.mappers.RequestMappers
import ru.rlrent.ui.navigation.routes.ChooseBillAmountDialogRoute
import ru.surfstudio.android.core.mvi.impls.ui.reactor.BaseReactorDependency
import ru.surfstudio.android.core.mvi.impls.ui.reducer.BaseReducer
import ru.surfstudio.android.core.mvi.ui.mapper.RequestMapper
import ru.surfstudio.android.core.mvp.binding.rx.relation.mvp.Command
import ru.surfstudio.android.core.mvp.binding.rx.relation.mvp.State
import ru.surfstudio.android.core.ui.state.LifecycleStage
import ru.surfstudio.android.dagger.scope.PerScreen
import javax.inject.Inject

internal data class ChooseBillAmountState(
    val route: ChooseBillAmountDialogRoute,
    val displayValues: List<String> = emptyList(),
    val chosenTimeIndex: Int = 0
)

/**
 * State Holder [ChooseBillAmountFragment]
 */
@PerScreen
internal class ChooseBillAmountStateHolder @Inject constructor(
    route: ChooseBillAmountDialogRoute
) : State<ChooseBillAmountState>(ChooseBillAmountState(route = route))

@PerScreen
internal class ChooseBillAmountCommandHolder @Inject constructor() {
    val openPaymentDialog = Command<Double>()
    val showMsg = Command<@StringRes Int>()
}

/**
 * Reducer [ChooseBillAmountFragment]
 */
@PerScreen
internal class ChooseBillAmountReducer @Inject constructor(
    dependency: BaseReactorDependency,
    private val ch: ChooseBillAmountCommandHolder
) : BaseReducer<ChooseBillAmountEvent, ChooseBillAmountState>(dependency) {

    override fun reduce(
        state: ChooseBillAmountState,
        event: ChooseBillAmountEvent
    ): ChooseBillAmountState {
        return when (event) {
            is ChooseBillAmountEvent.Lifecycle -> handleOnCreate(state, event)
            is ChooseBillAmountEvent.Input.SetBillAmountClicked -> state.copy(chosenTimeIndex = event.chosenTimeIndex)
            is ChooseBillAmountEvent.RequestPayment -> handleMakePayment(state, event)
            else -> state
        }
    }

    private fun handleOnCreate(
        state: ChooseBillAmountState,
        event: ChooseBillAmountEvent.Lifecycle
    ): ChooseBillAmountState {
        if (event.stage != LifecycleStage.CREATED) return state
        return state.copy(displayValues = PayInterval.displayValues)
    }

    private fun handleMakePayment(
        state: ChooseBillAmountState,
        event: ChooseBillAmountEvent.RequestPayment
    ): ChooseBillAmountState {
        val paymentRequest = RequestMapper.builder(event.request)
            .mapData(RequestMappers.data.default())
            .mapLoading(RequestMappers.loading.default())
            .handleError(RequestMappers.error.loadingBased(errorHandler))
            .reactOnSuccess { _, _, _ -> ch.showMsg.accept(R.string.payment_success) }
            .build()

        return state
    }
}
