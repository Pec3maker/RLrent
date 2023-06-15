package ru.rlrent.i_auth

import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import ru.rlrent.i_auth.dto.*
import ru.rlrent.i_network.generated.urls.AuthUrls.GET_USER_PATH
import ru.rlrent.i_network.generated.urls.AuthUrls.PAY_PATH
import ru.rlrent.i_network.generated.urls.AuthUrls.USER_AUTH_PATH
import ru.rlrent.i_network.generated.urls.AuthUrls.USER_LOGOUT_PATH
import ru.rlrent.i_network.generated.urls.AuthUrls.USER_REGISTER_PATH

/**
 * Api для авторизации
 */
interface AuthApi {

    @POST(USER_AUTH_PATH)
    fun login(@Body authBody: AuthBody): Single<TokenResponse>

    @POST(USER_REGISTER_PATH)
    fun register(@Body registrationBody: RegistrationBody): Single<Unit>

    @POST(USER_LOGOUT_PATH)
    fun logout(): Completable

    @GET(GET_USER_PATH)
    fun getUser(): Single<UserResponse>

    @POST(PAY_PATH)
    fun makePayment(@Body paymentBody: PaymentBody): Single<Unit>
}
