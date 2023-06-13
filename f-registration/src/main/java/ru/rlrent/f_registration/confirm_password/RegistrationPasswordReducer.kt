package ru.rlrent.f_registration.confirm_password

import ru.android.rlrent.f_registration.R
import ru.rlrent.f_registration.InputFieldUI
import ru.rlrent.f_registration.confirm_password.RegistrationPasswordEvent.Input
import ru.rlrent.f_registration.confirm_password.RegistrationPasswordEvent.Register
import ru.rlrent.i_network.error.AlreadyExistsException
import ru.rlrent.ui.mvi.mappers.RequestMappers
import ru.rlrent.ui.mvi.placeholder.LoadStateType
import ru.rlrent.ui.mvi.validation.impl.FieldValidatorImpl
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
 * State [RegistrationPasswordFragmentView]
 */
internal data class RegistrationPasswordState(
    val firstPasswordValidation: InputFieldUI? = null,
    val secondPasswordValidation: InputFieldUI? = null,
    val placeholderState: LoadStateType = LoadStateType.None()
) {
    val isDataValid: Boolean
        get() = firstPasswordValidation?.isValid == true &&
                secondPasswordValidation?.isValid == true
}

/**
 * State Holder [RegistrationPasswordFragmentView]
 */
@PerScreen
internal class RegistrationPasswordScreenStateHolder @Inject constructor() :
    State<RegistrationPasswordState>(RegistrationPasswordState())

/**
 * Command Holder [RegistrationPasswordFragmentView]
 */
@PerScreen
internal class RegistrationPasswordCommandHolder @Inject constructor() {
    val showMessage = Command<MessageUi>()
    val hideKeyboard = Command<Unit>()
}

/**
 * Reducer [RegistrationPasswordFragmentView]
 */
@PerScreen
internal class RegistrationPasswordReducer @Inject constructor(
    dependency: BaseReactorDependency,
    private val ch: RegistrationPasswordCommandHolder
) : BaseReducer<RegistrationPasswordEvent, RegistrationPasswordState>(dependency) {

    override fun reduce(
        state: RegistrationPasswordState,
        event: RegistrationPasswordEvent
    ): RegistrationPasswordState {
        return when (event) {
            is Input.ValidateFields -> validateScreen(state, event)
            is Register -> handleRegistrationRequest(state, event)
            else -> state
        }
    }

    private fun handleRegistrationRequest(
        state: RegistrationPasswordState,
        event: Register
    ): RegistrationPasswordState {
        val registrationRequest = RequestMapper.builder(event.request)
            .mapData(RequestMappers.data.default())
            .mapLoading(RequestMappers.loading.transparentOrNone())
            .handleSpecificError<AlreadyExistsException> { _, _, _ ->
                ch.showMessage.accept(MessageUi(R.string.already_exists_error_message))
                true
            }
            .handleError(RequestMappers.error.loadingBased(errorHandler))
            .reactOnSuccess { _ ->
                ch.showMessage.accept(MessageUi(R.string.registration_success_text, R.color.green))
            }
            .build()

        return state.copy(
            placeholderState = registrationRequest.placeholderState
        )
    }

    private fun validateScreen(
        state: RegistrationPasswordState,
        event: Input.ValidateFields
    ): RegistrationPasswordState {

        val firstPasswordValidationResult =
            FieldValidatorImpl.validate(event.firstPassword, passwordRulesSet)
        val secondPasswordValidationResult =
            FieldValidatorImpl.validate(event.secondPassword, passwordRulesSet)

        val isPasswordsEquals = event.firstPassword == event.secondPassword

        val newState = state.copy(
            firstPasswordValidation = InputFieldUI(
                isValid = firstPasswordValidationResult == null && isPasswordsEquals,
                messageRes = null
            ),
            secondPasswordValidation = InputFieldUI(
                isValid = secondPasswordValidationResult == null && isPasswordsEquals,
                messageRes = secondPasswordValidationResult ?: firstPasswordValidationResult
                ?: if (!isPasswordsEquals) R.string.registration_passwords_is_not_equal else null
            )
        )

        if (newState.isDataValid) {
            ch.hideKeyboard.accept()
        }

        return newState
    }
}
