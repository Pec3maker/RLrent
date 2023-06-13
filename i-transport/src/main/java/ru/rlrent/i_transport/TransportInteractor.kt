package ru.rlrent.i_transport

import android.annotation.SuppressLint
import io.reactivex.Single
import ru.rlrent.domain.transport.Transport
import ru.rlrent.domain.transport.Zone
import ru.rlrent.i_network.network.BaseNetworkInteractor
import ru.surfstudio.android.connection.ConnectionProvider
import ru.surfstudio.android.dagger.scope.PerApplication
import javax.inject.Inject

/**
 * Интерактор, отвечающий за транспорт
 */
@PerApplication
@SuppressLint("CheckResult")
class TransportInteractor @Inject constructor(
    connectionQualityProvider: ConnectionProvider,
    private val transportRepository: TransportRepository,
) : BaseNetworkInteractor(connectionQualityProvider) {

    /**
     * Запрос доступного транспорта
     */
    fun getTransportList(
        latitude: Double = 0.0,
        longitude: Double = 0.0,
        radius: Int = 100000000
    ): Single<List<Transport>> = transportRepository.getTransportList(latitude, longitude, radius)

    /**
     * Запрос доступного транспорта
     */
    fun getZonesList(
        latitude: Double = 0.0,
        longitude: Double = 0.0,
        radius: Int = 100000000
    ): Single<List<Zone>> = transportRepository.getZonesList(latitude, longitude, radius)
}
