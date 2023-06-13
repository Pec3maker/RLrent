package ru.rlrent.i_auth

import android.annotation.SuppressLint
import io.reactivex.Completable
import io.reactivex.Single
import ru.rlrent.domain.auth.LoginInfo
import ru.rlrent.domain.user.User
import ru.rlrent.i_auth.dto.AuthBody
import ru.rlrent.i_auth.dto.RegistrationBody
import ru.rlrent.i_network.network.BaseNetworkInteractor
import ru.rlrent.i_session.SessionChangedInteractor
import ru.surfstudio.android.connection.ConnectionProvider
import ru.surfstudio.android.dagger.scope.PerApplication
import javax.inject.Inject

/**
 * Интерактор, отвечающий за авторизацию пользователя
 */
@PerApplication
@SuppressLint("CheckResult")
class AuthInteractor @Inject constructor(
    connectionQualityProvider: ConnectionProvider,
    private val authRepository: AuthRepository,
    private val sessionChangedInteractor: SessionChangedInteractor,
) : BaseNetworkInteractor(connectionQualityProvider) {

    /**
     * Авторизация пользователя
     */
    fun login(login: String, password: String): Single<LoginInfo> {
        return authRepository.login(
            AuthBody(
                login = login,
                password = password
            )
        ).doOnSuccess { loginInfo ->
            sessionChangedInteractor.onLogin(loginInfo)
        }
    }

    /**
     * Регистрация пользователя
     */
    fun register(login: String, password: String, phone: String): Single<Unit> {
        return authRepository.register(
            RegistrationBody(
                login = login,
                password = password,
                passwordConfirm = password,
                phoneNumber = phone
            )
        )
    }

    /**
     * Выход текущего авторизованного пользователя
     */
    fun logout(): Completable {
        return authRepository.logout()
            .doOnComplete {
                sessionChangedInteractor.onForceLogout()
            }
    }

    /**
     * Запрос текущего юзера
     */
    fun getUser(): Single<User> = authRepository.getUser()
}
