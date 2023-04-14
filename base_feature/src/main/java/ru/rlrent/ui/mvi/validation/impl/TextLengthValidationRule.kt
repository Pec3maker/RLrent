package ru.rlrent.ui.mvi.validation.impl

import ru.rlrent.ui.mvi.validation.base.FieldValidationRule


/**
 * Проверка поля на определенное количество символов.
 *
 * @property maxLength максимальное количество символов (включительно), при котором поле считается валидным.
 * @property minLength минимальное количество символов, по достижении которого (включительно) поле считается валидным.
 * */
class TextLengthValidationRule<E>(
    private val minLength: Int,
    private val maxLength: Int,
    override val errorDescription: E? = null
) : FieldValidationRule<E> {

    private val lengthBounds: IntRange get() = (minLength..maxLength)

    override fun test(testSubject: String): Boolean = testSubject.length in lengthBounds
}
