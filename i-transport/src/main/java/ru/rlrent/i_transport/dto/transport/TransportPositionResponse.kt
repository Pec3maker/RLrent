package ru.rlrent.i_transport.dto.transport

import com.google.gson.annotations.SerializedName
import ru.rlrent.domain.transport.TransportPosition
import ru.rlrent.i_network.network.Transformable

data class TransportPositionResponse(
    @SerializedName("latitude") val latitude: Double = 0.0,
    @SerializedName("longitude") val longitude: Double = 0.0
) : Transformable<TransportPosition> {

    override fun transform(): TransportPosition {
        return TransportPosition(
            latitude = latitude,
            longitude = longitude
        )
    }
}