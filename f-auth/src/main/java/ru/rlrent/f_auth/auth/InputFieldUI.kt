package ru.rlrent.f_auth.auth

import androidx.annotation.StringRes

data class InputFieldUI(
    val isValid: Boolean,
    @StringRes val messageRes: Int?
)
