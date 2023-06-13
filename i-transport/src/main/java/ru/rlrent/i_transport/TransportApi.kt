package ru.rlrent.i_transport

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import ru.rlrent.i_network.generated.urls.TransportUrls.TRANSPORT_PATH
import ru.rlrent.i_network.generated.urls.TransportUrls.ZONES_PATH
import ru.rlrent.i_transport.dto.transport.TransportResponse
import ru.rlrent.i_transport.dto.zones.ZoneResponse

/**
 * Api для транспорта
 */
interface TransportApi {

    @GET(TRANSPORT_PATH)
    fun getTransportList(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("radius") radius: Int
    ): Single<List<TransportResponse>>

    @GET(ZONES_PATH)
    fun getZonesList(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("radius") radius: Int
    ): Single<List<ZoneResponse>>
}
