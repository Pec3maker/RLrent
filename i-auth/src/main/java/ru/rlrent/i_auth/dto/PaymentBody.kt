package ru.rlrent.i_auth.dto

import com.google.gson.annotations.SerializedName

data class PaymentBody(
    @SerializedName("payment_token") val paymentToken: String,
    @SerializedName("amount") val amountBody: AmountBody
)