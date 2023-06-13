package ru.rlrent.i_transport.dto.transport

import com.google.gson.annotations.SerializedName
import ru.rlrent.domain.transport.*
import ru.rlrent.i_network.network.Transformable
import ru.surfstudio.android.utilktx.ktx.text.EMPTY_STRING

data class TransportResponse(
    @SerializedName("id") val id: Int?,
    @SerializedName("code") val code: String?,
    @SerializedName("type") val type: TransportTypeResponse?,
    @SerializedName("status") val status: TransportStatusResponse?,
    @SerializedName("condition") val condition: TransportConditionResponse?,
    @SerializedName("manufacturer") val manufacturer: String?,
    @SerializedName("position") val position: TransportPositionResponse?,
    @SerializedName("maxSpeed") val maxSpeed: Int?,
    @SerializedName("charge") val charge: Int?
) : Transformable<Transport> {

    override fun transform(): Transport {
        return Transport(
            id = id ?: 0,
            code = code ?: EMPTY_STRING,
            transportType = type?.transform() ?: TransportType.BIKE,
            status = status?.transform() ?: TransportStatus.FREE,
            transportCondition = condition?.transform() ?: TransportCondition.GREAT,
            manufacturer = manufacturer ?: EMPTY_STRING,
            position = position?.transform() ?: TransportPosition(),
            maxSpeed = maxSpeed ?: 0,
            charge = charge ?: 0
        )
    }
}
