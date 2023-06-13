package ru.rlrent.i_transport.dto.transport

import com.google.gson.annotations.SerializedName
import ru.rlrent.domain.transport.TransportCondition
import ru.rlrent.i_network.network.Transformable

enum class TransportConditionResponse : Transformable<TransportCondition> {

    @SerializedName("GREAT")
    GREAT,

    @SerializedName("GOOD")
    GOOD,

    @SerializedName("SATISFYING")
    SATISFYING,

    @SerializedName("NOT_SATISFYING")
    NOT_SATISFYING;

    override fun transform(): TransportCondition {
        return when (this) {
            GREAT -> TransportCondition.GREAT
            GOOD -> TransportCondition.GOOD
            SATISFYING -> TransportCondition.SATISFYING
            NOT_SATISFYING -> TransportCondition.NOT_SATISFYING
        }
    }
}