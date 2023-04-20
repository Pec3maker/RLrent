package ru.rlrent.f_registration

import androidx.annotation.StringRes

data class InputFieldUI(
    val isValid: Boolean,
    @StringRes val messageRes: Int?
)
