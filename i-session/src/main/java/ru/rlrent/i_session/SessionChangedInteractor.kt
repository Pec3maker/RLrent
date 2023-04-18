package ru.rlrent.i_session

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import ru.surfstudio.android.dagger.scope.PerApplication
import ru.surfstudio.android.utilktx.ktx.text.EMPTY_STRING
import ru.rlrent.i_network.network.InvalidSessionListener
import ru.rlrent.i_token.TokenStorage
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
        get() = tokenStorage.refreshToken != EMPTY_STRING

    fun observeSessionChanged(): Observable<LoginState> {
        return sessionChangedPublishSubject
    }
//
//    fun onLogin(authUser: AuthUser, clearStorages: Boolean = true) {
//        if (clearStorages) {
//            clearStorage()
//        }
//        with(authUser) {
////            userStorage.currentUser = user
//            tokenStorage.accessToken = loginInfo.accessToken
//            tokenStorage.refreshToken = loginInfo.refreshToken ?: EMPTY_STRING
//        }
//        sessionChangedPublishSubject.onNext(LoginState.LOGGED_IN)
//    }

    fun onForceLogout() {
        clearStorage()
        sessionChangedPublishSubject.onNext(LoginState.NOT_AUTHORIZED)
    }

    private fun clearStorage() {
        tokenStorage.clearTokens()
//        userStorage.clear()
    }

    override fun onSessionInvalid() {
        onForceLogout()
    }
}
