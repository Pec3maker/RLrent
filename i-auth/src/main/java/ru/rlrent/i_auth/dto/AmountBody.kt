package ru.rlrent.i_auth.dto

import com.google.gson.annotations.SerializedName

data class AmountBody(
    @SerializedName("value") val value: Float,
    @SerializedName("currency") val currency: String = "RUB"
)
