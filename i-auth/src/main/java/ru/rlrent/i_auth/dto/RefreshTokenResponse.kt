package ru.rlrent.i_auth.dto

import com.google.gson.annotations.SerializedName
import ru.rlrent.domain.entity.LoginInfoEntity
import ru.rlrent.i_network.network.Transformable
import ru.surfstudio.android.utilktx.ktx.text.EMPTY_STRING

data class RefreshTokenResponse(
    @SerializedName("access_token") val accessToken: String?,
    @SerializedName("refresh_token") val refreshToken: String?
) : Transformable<LoginInfoEntity> {

    override fun transform(): LoginInfoEntity {
        return LoginInfoEntity(
            accessToken = accessToken ?: EMPTY_STRING,
            refreshToken = refreshToken ?: EMPTY_STRING
        )
    }
}
