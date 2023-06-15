package ru.rlrent.i_network.network.error;

/**
 * ошибка 304, см механизм Etag
 */
public class NotModifiedException extends HttpError {
    public NotModifiedException(Throwable cause, int code, String url) {
        super(cause, code, url);
    }
}
