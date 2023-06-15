package ru.rlrent.ui.dialog.choose_bill_amount

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.android.rlrent.base_feature.R
import ru.android.rlrent.base_feature.databinding.DialogChooseBillAmountBinding
import ru.rlrent.ui.dialog.choose_bill_amount.ChooseBillAmountEvent.Input
import ru.rlrent.ui.dialog.choose_bill_amount.ChooseBillAmountEvent.Input.SetBillAmountClicked
import ru.rlrent.ui.mvi.dialog.RoundCornersMviDialogFragment
import ru.rlrent.v_message_controller_top.IconMessageController
import ru.surfstudio.android.core.mvi.impls.event.hub.ScreenEventHub
import ru.surfstudio.android.core.ui.view_binding.viewBinding
import ru.yoomoney.sdk.kassa.payments.Checkout.createTokenizationResult
import ru.yoomoney.sdk.kassa.payments.Checkout.createTokenizeIntent
import ru.yoomoney.sdk.kassa.payments.checkoutParameters.Amount
import ru.yoomoney.sdk.kassa.payments.checkoutParameters.PaymentMethodType
import ru.yoomoney.sdk.kassa.payments.checkoutParameters.PaymentParameters
import ru.yoomoney.sdk.kassa.payments.checkoutParameters.SavePaymentMethod
import java.math.BigDecimal
import java.util.*
import javax.inject.Inject

/**
 * Диалог с оплатой
 * */
internal class ChooseBillAmountFragment :
    RoundCornersMviDialogFragment<ChooseBillAmountState, ChooseBillAmountEvent>() {

    @Inject
    override lateinit var hub: ScreenEventHub<ChooseBillAmountEvent>

    @Inject
    override lateinit var sh: ChooseBillAmountStateHolder

    @Inject
    lateinit var ch: ChooseBillAmountCommandHolder

    @Inject
    lateinit var mc: IconMessageController

    private val binding by viewBinding(DialogChooseBillAmountBinding::bind)

    override fun getScreenName(): String = "ChooseBillAmountFragment"

    override fun createConfigurator() = ChooseBillAmountConfigurator(arguments)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initTransparentWindowBackground()
        return inflater.inflate(R.layout.dialog_choose_bill_amount, container, false)
    }

    override fun initViews() {
        ch.openPaymentDialog.bindTo {
            val paymentMethodTypes = setOf(
                PaymentMethodType.BANK_CARD,
            )
            val paymentParameters = PaymentParameters(
                amount = Amount(BigDecimal.valueOf(it), Currency.getInstance("RUB")),
                title = "Пополнение баланса",
                subtitle = "Пополнить баланс в личном кабинете",
                clientApplicationKey = "test_MjE3NTQ5JbIdQQboo6p1dLaP2ZGI1SSgxY2SxzH0pYg",
                shopId = "217549",
                savePaymentMethod = SavePaymentMethod.OFF,
                paymentMethodTypes = paymentMethodTypes
            )

            val intent = createTokenizeIntent(requireContext(), paymentParameters)
            startActivityForResult(intent, 5)
        }

        ch.showMsg.bindTo {
            mc.show(it)
        }

        with(binding) {
            chooseBillAmountBtn.setOnClickListener {
                val chosenTimeIndex = chooseBillAmountNumberPicker.value
                SetBillAmountClicked(chosenTimeIndex).emit()
            }
        }
    }

    override fun render(state: ChooseBillAmountState) {
        with(binding.chooseBillAmountNumberPicker) {
            if (state.displayValues.isNotEmpty()) {
                displayedValues = state.displayValues.toTypedArray()
                minValue = 0
                maxValue = state.displayValues.size - 1
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 5) {
            if (resultCode == Activity.RESULT_OK) {
                // Обработка успешного результата
                val result = data?.let { createTokenizationResult(it) }
                result?.paymentToken?.let {
                    Input.MakePayment(it).emit()
                }

                Log.d("hehehehehheh!!!!", result?.paymentToken.toString())
            } else if (resultCode == Activity.RESULT_CANCELED) {
                // Обработка отмены
            }
        }
    }
}
