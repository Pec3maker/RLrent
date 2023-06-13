package ru.rlrent.i_transport

import io.reactivex.Single
import ru.rlrent.domain.transport.Transport
import ru.rlrent.domain.transport.Zone
import ru.rlrent.i_network.network.transformCollection
import ru.rlrent.i_network.service.BaseNetworkService
import ru.surfstudio.android.dagger.scope.PerApplication
import javax.inject.Inject

/**
 * Сервис, отвечающий за транспорт
 */
@PerApplication
class TransportRepository @Inject constructor(
    private val transportApi: TransportApi
) : BaseNetworkService() {

    /**
     * Получить список доступных тс
     */
    fun getTransportList(
        latitude: Double,
        longitude: Double,
        radius: Int
    ): Single<List<Transport>> =
        transportApi.getTransportList(latitude, longitude, radius).transformCollection()

    /**
     * Получить список доступных зон
     */
    fun getZonesList(
        latitude: Double,
        longitude: Double,
        radius: Int
    ): Single<List<Zone>> =
        transportApi.getZonesList(latitude, longitude, radius).transformCollection()
}
