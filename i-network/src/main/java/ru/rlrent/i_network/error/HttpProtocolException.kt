package ru.rlrent.i_network.error

import retrofit2.HttpException
import ru.rlrent.i_network.network.error.NetworkException

private fun prepareMessage(httpMessage: String, code: Int, url: String): String {
    return " httpCode=" + code + "\n" +
            ", httpMessage='" + httpMessage + "'" +
            ", url='" + url + "'"
}

/**
 * получен ответ не 2xx
 */
sealed class HttpProtocolException(
    cause: HttpException,
    val httpMessage: String,
    val httpCode: Int,
    url: String
) : NetworkException(prepareMessage(httpMessage, httpCode, url), cause)

/**
 * Отсутствует авторизация(401)
 */
class NonAuthorizedException(
    cause: HttpException,
    httpMessage: String,
    httpCode: Int,
    url: String
) : HttpProtocolException(cause, httpMessage, httpCode, url)

/**
 * Неверно введен лог/пас(400)
 */
class WrongCredentialsException(
    cause: HttpException,
    httpMessage: String,
    httpCode: Int,
    url: String
) : HttpProtocolException(cause, httpMessage, httpCode, url)

/**
 * Пользователь уже существует (302)
 */
class AlreadyExistsException(
    cause: HttpException,
    httpMessage: String,
    httpCode: Int,
    url: String
) : HttpProtocolException(cause, httpMessage, httpCode, url)

/**
 * Неизвестная ошибка
 */
class OtherHttpException(
    cause: HttpException,
    httpCode: Int,
    url: String
) : HttpProtocolException(cause, "Неизвестная ошибка", httpCode, url)
