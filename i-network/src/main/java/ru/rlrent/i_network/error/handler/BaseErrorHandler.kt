package ru.rlrent.i_network.error.handler

import ru.rlrent.i_network.error.HttpProtocolException

/**
 * Базовый класс обработки ошибок сервера
 */
interface BaseErrorHandler {

    fun handle(e: HttpProtocolException)
}
