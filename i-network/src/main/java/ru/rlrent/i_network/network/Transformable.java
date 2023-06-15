package ru.rlrent.i_network.network;

import ru.rlrent.i_network.network.response.BaseResponse;

/**
 * Интерфейс, указывающий что объект может быть трансформирован в объект типа T
 *
 * @param <T>
 */
public interface Transformable<T> extends BaseResponse {
    T transform();
}
