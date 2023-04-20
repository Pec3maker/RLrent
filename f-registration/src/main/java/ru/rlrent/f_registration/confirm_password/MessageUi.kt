package ru.rlrent.f_registration.confirm_password

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import ru.android.rlrent.f_registration.R

data class MessageUi(
    @StringRes val message: Int,
    @ColorRes val backgroundColor: Int = R.color.red
)
