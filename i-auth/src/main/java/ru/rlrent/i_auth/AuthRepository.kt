package ru.rlrent.i_auth

import io.reactivex.Completable
import io.reactivex.Single
import ru.rlrent.domain.auth.LoginInfo
import ru.rlrent.domain.user.User
import ru.rlrent.i_auth.dto.AuthBody
import ru.rlrent.i_auth.dto.PaymentBody
import ru.rlrent.i_auth.dto.RegistrationBody
import ru.rlrent.i_network.network.transform
import ru.rlrent.i_network.service.BaseNetworkService
import ru.surfstudio.android.dagger.scope.PerApplication
import javax.inject.Inject

/**
 * Сервис, отвечающий за авторизацию и регистрацию пользователя
 */
@PerApplication
class AuthRepository @Inject constructor(
    private val authApi: AuthApi
) : BaseNetworkService() {

    /**
     * Авторизация пользователя
     */
    fun login(authRequest: AuthBody): Single<LoginInfo> =
        authApi.login(authRequest).transform()

    /**
     * Регистрация пользователя
     */
    fun register(registrationRequest: RegistrationBody): Single<Unit> =
        authApi.register(registrationRequest)

    /**
     * Выход текущего авторизованного пользователя
     */
    fun logout(): Completable = authApi.logout()

    /**
     * Запрос текущего юзера
     */
    fun getUser(): Single<User> = authApi.getUser().transform()

    /**
     * Оплата
     */
    fun makePayment(paymentBody: PaymentBody): Single<Unit> = authApi.makePayment(paymentBody)
}
