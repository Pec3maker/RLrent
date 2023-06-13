package ru.rlrent.i_network.error.exception

/**
 * Ошибка, означающая, что к текущему запросу уже был произведен логаут и токена нет
 */
class AlreadyLogoutException(message: String = "Logout уже произошел") : BaseMessagedException(message)
