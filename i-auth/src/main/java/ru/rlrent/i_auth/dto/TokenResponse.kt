package ru.rlrent.i_auth.dto

import com.google.gson.annotations.SerializedName
import ru.rlrent.domain.auth.LoginInfo
import ru.rlrent.i_network.network.Transformable
import ru.surfstudio.android.utilktx.ktx.text.EMPTY_STRING

data class TokenResponse(
    @SerializedName("token") val token: String?,
) : Transformable<LoginInfo> {

    override fun transform(): LoginInfo {
        return LoginInfo(token = token ?: EMPTY_STRING)
    }
}
