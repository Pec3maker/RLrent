package ru.rlrent.ui.mvi.validation.validation

import androidx.annotation.StringRes
import ru.android.rlrent.base_feature.R
import ru.rlrent.ui.mvi.validation.impl.NotEmptyValidationRule
import ru.rlrent.ui.mvi.validation.impl.TextLengthValidationRule

private val notEmptyValidationRule = NotEmptyValidationRule<@StringRes Int?>(
    R.string.validation_empty_err
)

private val passwordLengthValidationRule = TextLengthValidationRule<@StringRes Int?>(
    8,
    256,
    R.string.validation_err_password
)

private val loginLengthValidationRule = TextLengthValidationRule<@StringRes Int?>(
    5,
    256,
    R.string.validation_err_login
)

private val phoneLengthValidationRule = TextLengthValidationRule<@StringRes Int?>(
    11,
    11,
    R.string.validation_err_phone
)

val loginRulesSet = setOf(
    notEmptyValidationRule,
    loginLengthValidationRule
)

val passwordRulesSet = setOf(
    notEmptyValidationRule,
    passwordLengthValidationRule
)

val phoneRulesSet = setOf(
    notEmptyValidationRule,
    phoneLengthValidationRule
)
