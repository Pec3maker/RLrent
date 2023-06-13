package ru.rlrent.ui.mvi.validation.base

interface FieldValidator<ValidatedObject, Result, Error> {

    fun <Rule : FieldValidationRule<Error>> validate(
        input: ValidatedObject,
        rules: Set<Rule>
    ): Result
}
