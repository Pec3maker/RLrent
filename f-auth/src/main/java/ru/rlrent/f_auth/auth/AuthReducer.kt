package ru.rlrent.f_auth.auth

import androidx.annotation.StringRes
import ru.android.rlrent.f_auth.R
import ru.rlrent.f_auth.auth.AuthEvent.Authorize
import ru.rlrent.f_auth.auth.AuthEvent.Input
import ru.rlrent.i_network.error.WrongCredentialsException
import ru.rlrent.ui.mvi.mappers.RequestMappers
import ru.rlrent.ui.mvi.placeholder.LoadStateType
import ru.rlrent.ui.mvi.validation.impl.FieldValidatorImpl
import ru.rlrent.ui.mvi.validation.validation.loginRulesSet
import ru.rlrent.ui.mvi.validation.validation.passwordRulesSet
import ru.rlrent.ui.util.placeholderState
import ru.surfstudio.android.core.mvi.impls.ui.reactor.BaseReactorDependency
import ru.surfstudio.android.core.mvi.impls.ui.reducer.BaseReducer
import ru.surfstudio.android.core.mvi.ui.mapper.RequestMapper
import ru.surfstudio.android.core.mvp.binding.rx.relation.mvp.Command
import ru.surfstudio.android.core.mvp.binding.rx.relation.mvp.State
import ru.surfstudio.android.dagger.scope.PerScreen
import javax.inject.Inject

/**
 * State [AuthFragmentView]
 */
internal data class AuthState(
    val loginValidation: InputFieldUI? = null,
    val passwordValidation: InputFieldUI? = null,
    val placeholderState: LoadStateType = LoadStateType.None()
) {
    val isDataValid: Boolean
        get() = loginValidation?.isValid == true &&
                passwordValidation?.isValid == true
}

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
    val hideKeyboard = Command<Unit>()
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
        return when (event) {
            is Input.SignIn -> validateScreen(state, event)
            is Authorize -> handleAuthorizationRequest(state, event)
            else -> state
        }
    }

    private fun handleAuthorizationRequest(
        state: AuthState,
        event: Authorize
    ): AuthState {
        val authRequest = RequestMapper.builder(event.request)
            .mapData(RequestMappers.data.default())
            .mapLoading(RequestMappers.loading.transparentOrNone())
            .handleSpecificError<WrongCredentialsException> { _, _, _ ->
                ch.showMessage.accept(R.string.wrong_credentials_error_message)
                true
            }
            .handleError(RequestMappers.error.loadingBased(errorHandler))
            .build()

        return state.copy(
            placeholderState = authRequest.placeholderState
        )
    }

    private fun validateScreen(
        state: AuthState,
        event: Input.SignIn
    ): AuthState {

        val loginValidationResult = FieldValidatorImpl.validate(event.login, loginRulesSet)
        val passwordValidationResult = FieldValidatorImpl.validate(event.password, passwordRulesSet)

        val newState = state.copy(
            loginValidation = InputFieldUI(
                isValid = loginValidationResult == null,
                messageRes = loginValidationResult
            ),
            passwordValidation = InputFieldUI(
                isValid = passwordValidationResult == null,
                messageRes = passwordValidationResult
            )
        )

        if (newState.isDataValid) {
            ch.hideKeyboard.accept()
        }

        return newState
    }
}
