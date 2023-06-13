package ru.rlrent.i_trip.dto

import com.google.gson.annotations.SerializedName
import ru.rlrent.domain.trip.TripStatus
import ru.rlrent.i_network.network.Transformable

enum class TripStatusResponse : Transformable<TripStatus> {

    @SerializedName("STARTED")
    STARTED,

    @SerializedName("CLOSED")
    CLOSED;

    override fun transform(): TripStatus {
        return when (this) {
            STARTED -> TripStatus.STARTED
            CLOSED -> TripStatus.CLOSED
        }
    }
}
