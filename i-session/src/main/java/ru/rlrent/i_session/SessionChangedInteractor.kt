package ru.rlrent.i_session

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import ru.rlrent.domain.auth.LoginInfo
import ru.rlrent.i_network.network.InvalidSessionListener
import ru.rlrent.i_token.TokenStorage
import ru.surfstudio.android.dagger.scope.PerApplication
import ru.surfstudio.android.utilktx.ktx.text.EMPTY_STRING
import javax.inject.Inject

/**
 * инкапсулирует действия, которые необходимо выполнить при смене сессии/пользователя
 */
@PerApplication
class SessionChangedInteractor @Inject constructor(
    private val tokenStorage: TokenStorage
) : InvalidSessionListener {

    private val sessionChangedPublishSubject = PublishSubject.create<LoginState>()

    val isLoggedIn: Boolean
        get() = tokenStorage.token != EMPTY_STRING

    fun observeSessionChanged(): Observable<LoginState> {
        return sessionChangedPublishSubject
    }

    fun onLogin(loginInfo: LoginInfo, clearStorages: Boolean = true) {
        if (clearStorages) {
            clearStorage()
        }
        tokenStorage.token = loginInfo.token

        sessionChangedPublishSubject.onNext(LoginState.LOGGED_IN)
    }

    fun onForceLogout() {
        clearStorage()
        sessionChangedPublishSubject.onNext(LoginState.NOT_AUTHORIZED)
    }

    private fun clearStorage() {
        tokenStorage.clearTokens()
    }

    override fun onSessionInvalid() {
        onForceLogout()
    }
}
