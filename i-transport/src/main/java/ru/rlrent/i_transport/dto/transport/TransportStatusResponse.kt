package ru.rlrent.i_transport.dto.transport

import com.google.gson.annotations.SerializedName
import ru.rlrent.domain.transport.TransportStatus
import ru.rlrent.i_network.network.Transformable

enum class TransportStatusResponse : Transformable<TransportStatus> {

    @SerializedName("BUSY")
    BUSY,

    @SerializedName("FREE")
    FREE,

    @SerializedName("BROKEN")
    BROKEN,

    @SerializedName("DECOMMISSIONED")
    DECOMMISSIONED,

    @SerializedName("MAINTENANCE_REQUIRE")
    MAINTENANCE_REQUIRE;

    override fun transform(): TransportStatus {
        return when (this) {
            BUSY -> TransportStatus.BUSY
            FREE -> TransportStatus.FREE
            BROKEN -> TransportStatus.BROKEN
            DECOMMISSIONED -> TransportStatus.DECOMMISSIONED
            MAINTENANCE_REQUIRE -> TransportStatus.MAINTENANCE_REQUIRE
        }
    }
}
