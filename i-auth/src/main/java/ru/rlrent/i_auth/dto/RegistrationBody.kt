package ru.rlrent.i_auth.dto

import com.google.gson.annotations.SerializedName

data class RegistrationBody(
    @SerializedName("login") val login: String,
    @SerializedName("password") val password: String,
    @SerializedName("passwordConfirm") val passwordConfirm: String,
    @SerializedName("phoneNumber") val phoneNumber: String
)
