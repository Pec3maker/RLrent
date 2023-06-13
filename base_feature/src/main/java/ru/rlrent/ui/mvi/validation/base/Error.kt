package ru.rlrent.ui.mvi.validation.base

interface Error<E> {
    val errorDescription: E?
        get() = null
}
