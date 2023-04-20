package ru.rlrent.f_registration.name

import androidx.annotation.StringRes
import ru.rlrent.f_registration.InputFieldUI
import ru.rlrent.f_registration.name.RegistrationNameEvent.Input
import ru.rlrent.ui.mvi.validation.impl.FieldValidatorImpl
import ru.rlrent.ui.mvi.validation.validation.loginRulesSet
import ru.rlrent.ui.mvi.validation.validation.phoneRulesSet
import ru.surfstudio.android.core.mvi.impls.ui.reactor.BaseReactorDependency
import ru.surfstudio.android.core.mvi.impls.ui.reducer.BaseReducer
import ru.surfstudio.android.core.mvp.binding.rx.relation.mvp.Command
import ru.surfstudio.android.core.mvp.binding.rx.relation.mvp.State
import ru.surfstudio.android.dagger.scope.PerScreen
import javax.inject.Inject

/**
 * State [RegistrationNameFragmentView]
 */
internal data class RegistrationNameState(
    val nameValidation: InputFieldUI? = null,
    val phoneValidation: InputFieldUI? = null
) {
    val isDataValid: Boolean
        get() = nameValidation?.isValid == true &&
                phoneValidation?.isValid == true
}

/**
 * State Holder [RegistrationNameFragmentView]
 */
@PerScreen
internal class RegistrationNameScreenStateHolder @Inject constructor() :
    State<RegistrationNameState>(RegistrationNameState())

/**
 * Command Holder [RegistrationNameFragmentView]
 */
@PerScreen
internal class RegistrationNameCommandHolder @Inject constructor() {
    val showMessage = Command<@StringRes Int>()
    val hideKeyboard = Command<Unit>()
}

/**
 * Reducer [RegistrationNameFragmentView]
 */
@PerScreen
internal class RegistrationNameReducer @Inject constructor(
    dependency: BaseReactorDependency,
    private val ch: RegistrationNameCommandHolder
) : BaseReducer<RegistrationNameEvent, RegistrationNameState>(dependency) {

    override fun reduce(
        state: RegistrationNameState,
        event: RegistrationNameEvent
    ): RegistrationNameState {
        return when (event) {
            is Input.ValidateFields -> validateScreen(state, event)
            else -> state
        }
    }

    private fun validateScreen(
        state: RegistrationNameState,
        event: Input.ValidateFields
    ): RegistrationNameState {

        val nameValidationResult = FieldValidatorImpl.validate(event.name, loginRulesSet)
        val phoneValidationResult =
            FieldValidatorImpl.validate(event.number.filter { it.isDigit() }, phoneRulesSet)

        val newState = state.copy(
            nameValidation = InputFieldUI(
                isValid = nameValidationResult == null,
                messageRes = nameValidationResult
            ),
            phoneValidation = InputFieldUI(
                isValid = phoneValidationResult == null,
                messageRes = phoneValidationResult
            )
        )

        if (newState.isDataValid) {
            ch.hideKeyboard.accept()
        }

        return newState
    }
}
