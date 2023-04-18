package ru.rlrent.i_auth

import io.reactivex.Completable
import ru.surfstudio.android.dagger.scope.PerApplication
import javax.inject.Inject

/**
 * Сервис, отвечающий за авторизацию и регистрацию пользователя
 */
@PerApplication
class AuthRepository @Inject constructor(
    private val authApi: AuthApi
) : ru.rlrent.i_network.service.BaseNetworkService() {

//    /**
//     * Авторизация пользователя
//     */
//    fun login(authRequest: AuthBody): Single<AuthUser> =
//        authApi.login(authRequest).transform()

    /**
     * Выход текущего авторизованного пользователя
     */
    fun logout(): Completable = authApi.logout()
}
