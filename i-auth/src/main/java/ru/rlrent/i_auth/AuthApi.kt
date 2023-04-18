package ru.rlrent.i_auth

import io.reactivex.Completable
import retrofit2.http.Body
import retrofit2.http.POST
import ru.rlrent.i_network.generated.urls.AuthUrls.USER_LOGOUT_PATH

/**
 * Api для авторизации
 */
interface AuthApi {

//    @POST(USER_AUTH_PATH)
//    fun login(@Body authBody: AuthBody): Single<AuthResponse>

    @POST(USER_LOGOUT_PATH)
    fun logout(@Body emptyBody: Any = Any()): Completable // todo Body должно быть пустым
}
