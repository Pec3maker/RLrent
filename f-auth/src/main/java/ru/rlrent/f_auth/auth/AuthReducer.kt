package ru.rlrent.f_auth.auth

import androidx.annotation.StringRes
import ru.surfstudio.android.core.mvi.impls.ui.reactor.BaseReactorDependency
import ru.surfstudio.android.core.mvi.impls.ui.reducer.BaseReducer
import ru.surfstudio.android.core.mvp.binding.rx.relation.mvp.Command
import ru.surfstudio.android.core.mvp.binding.rx.relation.mvp.State
import ru.surfstudio.android.dagger.scope.PerScreen
import javax.inject.Inject

/**
 * State [AuthFragmentView]
 */
internal class AuthState

/**
 * State Holder [AuthFragmentView]
 */
@PerScreen
internal class AuthScreenStateHolder @Inject constructor() : State<AuthState>(AuthState())

/**
 * Command Holder [AuthFragmentView]
 */
@PerScreen
internal class AuthCommandHolder @Inject constructor() {
    val showMessage = Command<@StringRes Int>()
}

/**
 * Reducer [AuthFragmentView]
 */
@PerScreen
internal class AuthReducer @Inject constructor(
    dependency: BaseReactorDependency,
    private val ch: AuthCommandHolder
) : BaseReducer<AuthEvent, AuthState>(dependency) {

    override fun reduce(state: AuthState, event: AuthEvent): AuthState {
        return state
    }
}
