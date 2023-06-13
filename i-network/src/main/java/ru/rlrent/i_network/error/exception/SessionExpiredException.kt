package ru.rlrent.i_network.error.exception

/**
 * Ошибка, означающая, что текущая сессия пользователя истекла
 */
class SessionExpiredException(message: String = "Сессия пользователя истекла") : BaseMessagedException(message)
