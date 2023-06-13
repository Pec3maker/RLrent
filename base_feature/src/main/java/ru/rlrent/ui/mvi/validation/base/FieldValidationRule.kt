package ru.rlrent.ui.mvi.validation.base

/**
 * [ValidationRule] для полей ввода.
 */
interface FieldValidationRule<E> : ValidationRule<String>, Error<E>
