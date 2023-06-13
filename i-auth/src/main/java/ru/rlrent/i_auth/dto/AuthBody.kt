package ru.rlrent.i_auth.dto

import com.google.gson.annotations.SerializedName

data class AuthBody(
    @SerializedName("login") val login: String,
    @SerializedName("password") val password: String
)
