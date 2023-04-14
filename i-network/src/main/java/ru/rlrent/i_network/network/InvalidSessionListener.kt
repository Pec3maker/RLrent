package ru.rlrent.i_network.network

/**
 * Интерфейс, который необходимо реализовать, если класс должен реагировать на истечение сессии
 */
interface InvalidSessionListener {
    fun onSessionInvalid()
}
