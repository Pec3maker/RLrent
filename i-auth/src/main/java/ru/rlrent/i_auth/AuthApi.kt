package ru.rlrent.i_auth

import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST
import ru.rlrent.i_auth.dto.AuthBody
import ru.rlrent.i_auth.dto.AuthResponse
import ru.rlrent.i_network.generated.urls.AuthUrls.USER_AUTH_PATH
import ru.rlrent.i_network.generated.urls.AuthUrls.USER_LOGOUT_PATH

/**
 * Api для авторизации
 */
interface AuthApi {

    @POST(USER_AUTH_PATH)
    fun login(@Body authBody: AuthBody): Single<AuthResponse>

    @POST(USER_LOGOUT_PATH)
    fun logout(@Body emptyBody: Any = Any()): Completable // todo Body должно быть пустым
}
