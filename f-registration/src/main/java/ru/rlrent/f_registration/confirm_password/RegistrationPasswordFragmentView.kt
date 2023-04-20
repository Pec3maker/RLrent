package ru.rlrent.f_registration.confirm_password

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.WindowInsetsCompat.Type.systemBars
import com.jakewharton.rxbinding2.view.clicks
import com.thapl.ml.v_textfield.utils.updateValidationStatus
import ru.android.rlrent.f_registration.R
import ru.android.rlrent.f_registration.databinding.FragmentRegistrationPasswordBinding
import ru.rlrent.f_registration.confirm_password.RegistrationPasswordEvent.Input
import ru.rlrent.f_registration.confirm_password.di.RegistrationPasswordScreenConfigurator
import ru.rlrent.ui.mvi.placeholder.LoadStateView
import ru.rlrent.ui.mvi.placeholder.loadstate.renderer.DefaultLoadStateRenderer
import ru.rlrent.ui.mvi.view.BaseMviFragmentView
import ru.rlrent.ui.util.*
import ru.rlrent.v_message_controller_top.IconMessageController
import ru.surfstudio.android.core.mvi.impls.event.hub.ScreenEventHub
import ru.surfstudio.android.core.mvp.loadstate.BaseLoadStateRenderer
import ru.surfstudio.android.core.ui.navigation.feature.route.feature.CrossFeatureFragment
import ru.surfstudio.android.core.ui.view_binding.viewBinding
import javax.inject.Inject

/**
 * Вью аутентификации
 */
internal class RegistrationPasswordFragmentView :
    BaseMviFragmentView<RegistrationPasswordState, RegistrationPasswordEvent>(),
    CrossFeatureFragment,
    LoadStateView {
    @Inject
    override lateinit var hub: ScreenEventHub<RegistrationPasswordEvent>

    @Inject
    override lateinit var sh: RegistrationPasswordScreenStateHolder

    @Inject
    lateinit var ch: RegistrationPasswordCommandHolder

    @Inject
    lateinit var mc: IconMessageController

    private val binding: FragmentRegistrationPasswordBinding by viewBinding(
        FragmentRegistrationPasswordBinding::bind
    )

    override val renderer: BaseLoadStateRenderer by lazy {
        DefaultLoadStateRenderer(binding.registrationPhv) { }
    }

    override fun createConfigurator() = RegistrationPasswordScreenConfigurator(arguments)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_registration_password, container, false)
    }

    override fun onStop() {
        super.onStop()
        clearFocus()
    }

    override fun render(state: RegistrationPasswordState) {
        with(binding) {
            firstPasswordTf.updateValidationStatus(state.firstPasswordValidation?.messageRes)
            secondPasswordTf.updateValidationStatus(state.secondPasswordValidation?.messageRes)

            registrationPhv.performIfChanged(state.placeholderState) { placeholderState ->
                renderLoadState(placeholderState)
            }
        }
    }

    override fun initInsets() {
        val activity = requireActivity()
        activity.setSystemBarColor(false)

        with(binding) {
            activity.onKeyboardChanges { height ->
                policyTv.updateMargin(bottom = height)
            }
            screenContainer paddingTo systemBars()
        }
    }

    override fun initViews() {
        initCommands()
        addDefaultOnBackPressedCallback { Input.BackClicked.emit() }
        hideKeyboardOutsideEditTextTouch()

        with(binding) {
            backBtn.clicks().emit(Input.BackClicked)
            policyTv.clicks().emit(Input.OpenPolicy)

            firstPasswordTf.setOnNextButton { secondPasswordTf.requestFocus() }
            secondPasswordTf.setOnDoneButton {
                Input.ValidateFields(
                    firstPassword = firstPasswordTf.text,
                    secondPassword = secondPasswordTf.text
                ).emit()
            }
            registerBtn.setOnClickListener {
                Input.ValidateFields(
                    firstPassword = firstPasswordTf.text,
                    secondPassword = secondPasswordTf.text
                ).emit()
            }
        }
    }

    private fun initCommands() {
        ch.hideKeyboard.bindTo { requireActivity().hideKeyboard() }
        ch.showMessage.bindTo { mc.show(it.message, it.backgroundColor) }
    }

    private fun clearFocus() {
        with(binding) {
            firstPasswordTf.clearFocus()
            secondPasswordTf.clearFocus()
        }
    }
}
