package ru.rlrent.i_transport.dto.zones

import com.google.gson.annotations.SerializedName
import ru.rlrent.domain.transport.*
import ru.rlrent.i_network.network.Transformable
import ru.rlrent.i_transport.dto.transport.TransportPositionResponse
import ru.surfstudio.android.utilktx.ktx.text.EMPTY_STRING

data class ZoneResponse(
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("type") val type: String?,
    @SerializedName("status") val status: String?,
    @SerializedName("radius") val radius: Int?,
    @SerializedName("center") val center: TransportPositionResponse?
) : Transformable<Zone> {

    override fun transform(): Zone {
        return Zone(
            id = id ?: 0,
            name = name ?: EMPTY_STRING,
            radius = radius ?: 0,
            center = center?.transform() ?: TransportPosition()
        )
    }
}
