package ru.rlrent.f_auth

import androidx.annotation.StringRes
import ru.surfstudio.android.core.mvi.impls.ui.reactor.BaseReactorDependency
import ru.surfstudio.android.core.mvi.impls.ui.reducer.BaseReducer
import ru.surfstudio.android.core.mvi.ui.mapper.RequestMapper
import ru.surfstudio.android.core.mvp.binding.rx.relation.mvp.Command
import ru.surfstudio.android.core.mvp.binding.rx.relation.mvp.State
import ru.surfstudio.android.dagger.scope.PerScreen
import ru.surfstudio.android.template.base_feature.R
import ru.surfstudio.practice.f_auth.validation.loginRulesSet
import ru.surfstudio.practice.f_auth.validation.passwordRulesSet
import ru.surfstudio.practice.i_network.error.WrongCredentialsException
import ru.surfstudio.practice.ui.mvi.mappers.RequestMappers
import ru.surfstudio.practice.ui.mvi.validation.impl.FieldValidatorImpl
import ru.surfstudio.practice.ui.placeholder.LoadStateType
import ru.surfstudio.practice.ui.util.placeholderState
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
    val hideKeyboard = Command<Unit>()
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
        return when (event) {
            is AuthEvent.Input.SignIn -> validateScreen(state, event)
            is AuthEvent.Authorize -> handleAuthorizationRequest(state, event)
            else -> state
        }
    }

    private fun handleAuthorizationRequest(
        state: AuthState,
        event: AuthEvent.Authorize
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
        event: AuthEvent.Input.SignIn
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
