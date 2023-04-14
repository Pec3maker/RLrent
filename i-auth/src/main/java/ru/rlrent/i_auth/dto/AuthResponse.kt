package ru.rlrent.i_auth.dto

import com.google.gson.annotations.SerializedName
import ru.surfstudio.android.utilktx.ktx.text.EMPTY_STRING
import ru.rlrent.domain.auth.AuthUser
import ru.rlrent.domain.entity.LoginInfoEntity
import ru.rlrent.domain.users.User
import ru.rlrent.i_network.network.Transformable

data class AuthResponse(
    @SerializedName("access_token") val accessToken: String?,
    @SerializedName("refresh_token") val refreshToken: String?,
) : Transformable<AuthUser> {

    override fun transform(): AuthUser {
        return AuthUser(
            loginInfo = LoginInfoEntity(
                accessToken = accessToken ?: EMPTY_STRING,
                refreshToken = refreshToken ?: EMPTY_STRING
            ),
            user = User()
        )
    }
}
