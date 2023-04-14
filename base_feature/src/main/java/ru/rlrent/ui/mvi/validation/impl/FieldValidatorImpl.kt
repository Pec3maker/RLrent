package ru.rlrent.ui.mvi.validation.impl

import ru.rlrent.ui.mvi.validation.base.FieldValidationRule
import ru.rlrent.ui.mvi.validation.base.FieldValidator


object FieldValidatorImpl : FieldValidator<String, Int?, Int?> {

    override fun <Rule : FieldValidationRule<Int?>> validate(
        input: String,
        rules: Set<Rule>
    ): Int? {
        return rules.firstOrNull { !it.test(input) }?.errorDescription
    }
}
