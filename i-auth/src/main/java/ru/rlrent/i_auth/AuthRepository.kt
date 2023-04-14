package ru.rlrent.i_auth

import io.reactivex.Completable
import io.reactivex.Single
import ru.surfstudio.android.dagger.scope.PerApplication
import ru.rlrent.domain.auth.AuthUser
import ru.surfstudio.practice.i_auth.dto.AuthBody
import ru.surfstudio.practice.i_network.network.transform
import ru.rlrent.i_network.service.BaseNetworkService
import javax.inject.Inject

/**
 * Сервис, отвечающий за авторизацию и регистрацию пользователя
 */
@PerApplication
class AuthRepository @Inject constructor(
    private val authApi: AuthApi
) : ru.rlrent.i_network.service.BaseNetworkService() {

    /**
     * Авторизация пользователя
     */
    fun login(authRequest: AuthBody): Single<AuthUser> =
        authApi.login(authRequest).transform()

    /**
     * Выход текущего авторизованного пользователя
     */
    fun logout(): Completable = authApi.logout()
}
