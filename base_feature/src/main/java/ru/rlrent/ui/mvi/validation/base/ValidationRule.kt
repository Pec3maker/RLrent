package ru.rlrent.ui.mvi.validation.base

/**
 * Интерфейс сущности, определяющей правила валидации объектов типа [T].
 */
interface ValidationRule<T> {

    /**
     * Проверка [testSubject] на соответствие определенным правилам валидации.
     *
     * @param testSubject проверяемый объект.
     *
     * @return true, если правила валидации соблюдены, false в противном случае.
     */
    fun test(testSubject: T): Boolean
}
