package ru.rlrent.f_registration.name

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.WindowInsetsCompat.Type.systemBars
import com.jakewharton.rxbinding2.view.clicks
import com.thapl.ml.v_textfield.utils.updateValidationStatus
import ru.android.rlrent.f_registration.R
import ru.android.rlrent.f_registration.databinding.FragmentRegistrationNameBinding
import ru.rlrent.f_registration.name.RegistrationNameEvent.Input
import ru.rlrent.f_registration.name.di.RegistrationNameScreenConfigurator
import ru.rlrent.ui.mvi.view.BaseMviFragmentView
import ru.rlrent.ui.util.*
import ru.rlrent.v_message_controller_top.IconMessageController
import ru.surfstudio.android.core.mvi.impls.event.hub.ScreenEventHub
import ru.surfstudio.android.core.ui.navigation.feature.route.feature.CrossFeatureFragment
import ru.surfstudio.android.core.ui.view_binding.viewBinding
import javax.inject.Inject

/**
 * Вью аутентификации
 */
internal class RegistrationNameFragmentView :
    BaseMviFragmentView<RegistrationNameState, RegistrationNameEvent>(),
    CrossFeatureFragment {
    @Inject
    override lateinit var hub: ScreenEventHub<RegistrationNameEvent>

    @Inject
    override lateinit var sh: RegistrationNameScreenStateHolder

    @Inject
    lateinit var ch: RegistrationNameCommandHolder

    @Inject
    lateinit var mc: IconMessageController

    private val binding: FragmentRegistrationNameBinding by viewBinding(
        FragmentRegistrationNameBinding::bind
    )

    override fun createConfigurator() = RegistrationNameScreenConfigurator(arguments)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_registration_name, container, false)
    }

    override fun onResume() {
        super.onResume()
        with(binding) {
            phoneTf.clearText()
            nameTf.clearText()
        }
    }

    override fun onStop() {
        super.onStop()
        clearFocus()
    }

    override fun render(state: RegistrationNameState) {
        with(binding) {
            nameTf.updateValidationStatus(state.nameValidation?.messageRes)
            phoneTf.updateValidationStatus(state.phoneValidation?.messageRes)
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

            phoneTf.setUpMask(PHONE_MASK)
            nameTf.setOnNextButton { phoneTf.requestFocus() }
            phoneTf.setOnDoneButton {
                Input.ValidateFields(
                    name = nameTf.text,
                    number = phoneTf.text
                ).emit()
            }
            enterBtn.setOnClickListener {
                Input.ValidateFields(
                    name = nameTf.text,
                    number = phoneTf.text
                ).emit()
            }
        }
    }

    private fun initCommands() {
        ch.hideKeyboard.bindTo { requireActivity().hideKeyboard() }
        ch.showMessage.bindTo { mc.show(it) }
    }

    private fun clearFocus() {
        with(binding) {
            phoneTf.clearFocus()
            nameTf.clearFocus()
        }
    }

    companion object {
        private const val COUNTRY_CODE = "+7"
        const val PHONE_MASK = "$COUNTRY_CODE ([000]) [000] [00] [00]"
    }
}
