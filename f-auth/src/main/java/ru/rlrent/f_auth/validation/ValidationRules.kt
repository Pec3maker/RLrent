package ru.rlrent.f_auth.validation

import androidx.annotation.StringRes
import ru.surfstudio.android.template.f_auth.R
import ru.surfstudio.practice.ui.mvi.validation.impl.NotEmptyValidationRule
import ru.surfstudio.practice.ui.mvi.validation.impl.TextLengthValidationRule

private val notEmptyValidationRule = NotEmptyValidationRule<@StringRes Int?>(
    R.string.validation_empty_err
)

private val passwordLengthValidationRule = TextLengthValidationRule<@StringRes Int?>(
    6,
    256,
    R.string.validation_err_password
)

private val loginLengthValidationRule = TextLengthValidationRule<@StringRes Int?>(
    4,
    Int.MAX_VALUE,
    R.string.validation_err_login
)

internal val loginRulesSet = setOf(
    notEmptyValidationRule,
    loginLengthValidationRule
)

internal val passwordRulesSet = setOf(
    notEmptyValidationRule,
    passwordLengthValidationRule
)
