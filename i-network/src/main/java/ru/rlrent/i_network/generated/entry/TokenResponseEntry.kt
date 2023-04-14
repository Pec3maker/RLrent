package ru.rlrent.i_network.generated.entry

import com.google.gson.annotations.SerializedName
import ru.rlrent.domain.entity.LoginInfoEntity
import ru.rlrent.i_network.error.exception.InvalidServerValuesResponse


/**
 * сущность для ответа сервера с токенами
 */
data class TokenResponseEntry(
    @SerializedName("access_token") private val accessToken: String? = null,
    @SerializedName("refresh_token") private val refreshToken: String? = null
) : ru.rlrent.i_network.network.Transformable<LoginInfoEntity> {

    override fun transform(): LoginInfoEntity {
        if (accessToken == null) {
            throw InvalidServerValuesResponse(Pair("accessToken", "null"))
        }
        return LoginInfoEntity(accessToken, refreshToken)
    }
}