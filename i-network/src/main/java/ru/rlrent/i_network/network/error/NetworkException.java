package ru.rlrent.i_network.network.error;

/**
 * базовый класс для всех ошибок, возникающих при работе с сервером
 */
public abstract class NetworkException extends RuntimeException {
    public NetworkException() {
    }

    public NetworkException(String message) {
        super(message);
    }

    public NetworkException(Throwable cause) {
        super(cause);
    }

    public NetworkException(String message, Throwable cause) {
        super(message, cause);
    }
}
