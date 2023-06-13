package ru.rlrent.i_transport.dto.transport

import com.google.gson.annotations.SerializedName
import ru.rlrent.domain.transport.TransportType
import ru.rlrent.i_network.network.Transformable

enum class TransportTypeResponse : Transformable<TransportType> {

    @SerializedName("ELECTRIC_SCOOTER")
    ELECTRIC_SCOOTER,

    @SerializedName("BIKE")
    BIKE;

    override fun transform(): TransportType {
        return when (this) {
            ELECTRIC_SCOOTER -> TransportType.ELECTRIC_SCOOTER
            BIKE -> TransportType.BIKE
        }
    }
}