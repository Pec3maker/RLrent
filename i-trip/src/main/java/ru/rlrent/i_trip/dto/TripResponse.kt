package ru.rlrent.i_trip.dto

import com.google.gson.annotations.SerializedName
import ru.rlrent.domain.transport.*
import ru.rlrent.domain.trip.Trip
import ru.rlrent.domain.trip.TripStatus
import ru.rlrent.i_network.network.Transformable
import ru.surfstudio.android.utilktx.ktx.text.EMPTY_STRING

data class TripResponse(
    @SerializedName("id") val id: Int?,
    @SerializedName("login") val login: String?,
    @SerializedName("transportId") val transportId: Int?,
    @SerializedName("worth") val worth: Double?,
    @SerializedName("startAt") val startAt: String?,
    @SerializedName("finishAt") val finishAt: String?,
    @SerializedName("status") val status: TripStatusResponse?
) : Transformable<Trip> {

    override fun transform(): Trip {
        return Trip(
            id = id ?: 0,
            login = login ?: EMPTY_STRING,
            transportId = transportId ?: 0,
            worth = worth ?: 0.0,
            startAt = startAt ?: EMPTY_STRING,
            finishAt = finishAt ?: EMPTY_STRING,
            status = status?.transform() ?: TripStatus.CLOSED,
        )
    }
}
