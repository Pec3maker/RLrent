package ru.rlrent.ui.mvi.validation.impl

import ru.rlrent.ui.mvi.validation.base.FieldValidationRule


/**
 * Правило валидации полей, которые не должны быть пустыми.
 */
class NotEmptyValidationRule<E>(
    override val errorDescription: E? = null
) : FieldValidationRule<E> {

    override fun test(testSubject: String): Boolean = testSubject.isNotEmpty()
}
