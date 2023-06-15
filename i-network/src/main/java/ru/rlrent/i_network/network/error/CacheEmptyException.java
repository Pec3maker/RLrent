package ru.rlrent.i_network.network.error;

/**
 * внутренне исключение для механизма работы с сервером, используется для возвращения null в случае
 * попытки получения кеша, которого не существует
 */
public class CacheEmptyException extends NetworkException {
}
