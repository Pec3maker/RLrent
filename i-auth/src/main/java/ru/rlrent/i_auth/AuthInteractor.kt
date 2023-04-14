package ru.rlrent.i_auth

import android.annotation.SuppressLint
import io.reactivex.Completable
import io.reactivex.Single
import ru.rlrent.domain.auth.AuthUser
import ru.rlrent.i_auth.dto.AuthBody
import ru.rlrent.i_network.network.BaseNetworkInteractor
import ru.rlrent.i_session.SessionChangedInteractor
import ru.rlrent.i_token.TokenStorage
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
    private val tokenStorage: TokenStorage
) : BaseNetworkInteractor(connectionQualityProvider) {

    /**
     * Авторизация пользователя
     */
    fun login(login: String, password: String): Single<AuthUser> {
        return authRepository.login(
            AuthBody(
                login = login,
                password = password
            )
        ).doOnSuccess { authUser ->
            sessionChangedInteractor.onLogin(authUser)
        }
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
}
