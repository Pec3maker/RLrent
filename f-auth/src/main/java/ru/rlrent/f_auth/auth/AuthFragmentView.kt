package ru.rlrent.f_auth.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.WindowInsetsCompat.Type.systemBars
import com.thapl.ml.v_textfield.utils.updateValidationStatus
import ru.android.rlrent.f_auth.R
import ru.android.rlrent.f_auth.databinding.FragmentAuthBinding
import ru.rlrent.f_auth.auth.AuthEvent.Input
import ru.rlrent.f_auth.auth.di.AuthScreenConfigurator
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
internal class AuthFragmentView :
    BaseMviFragmentView<AuthState, AuthEvent>(),
    CrossFeatureFragment,
    LoadStateView {
    @Inject
    override lateinit var hub: ScreenEventHub<AuthEvent>

    @Inject
    override lateinit var sh: AuthScreenStateHolder

    @Inject
    lateinit var ch: AuthCommandHolder

    @Inject
    lateinit var mc: IconMessageController

    private val binding: FragmentAuthBinding by viewBinding(FragmentAuthBinding::bind)

    override val renderer: BaseLoadStateRenderer by lazy {
        DefaultLoadStateRenderer(binding.authPhv) { }
    }

    override fun createConfigurator() = AuthScreenConfigurator(arguments)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_auth, container, false)
    }

    override fun onResume() {
        super.onResume()
        with(binding) {
            loginTf.clearText()
            passwordTf.clearText()
        }
    }

    override fun onStop() {
        super.onStop()
        clearFocus()
    }

    override fun render(state: AuthState) {
        with(binding) {
            loginTf.updateValidationStatus(state.loginValidation?.messageRes)
            passwordTf.updateValidationStatus(state.passwordValidation?.messageRes)
            authPhv.performIfChanged(state.placeholderState) { placeholderState ->
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
        hideKeyboardOutsideEditTextTouch()

        with(binding) {
            loginTf.setOnNextButton {
                passwordTf.editText.requestFocus()
            }
            passwordTf.setOnDoneButton {
                Input.SignIn(loginTf.text, passwordTf.text).emit()
            }
            enterBtn.setOnClickListener {
                Input.SignIn(loginTf.text, passwordTf.text).emit()
            }

            policyTv.setOnClickListener {
                Input.OpenPolicy.emit()
            }
            notRegisteredTv.setOnClickListener {
                Input.OpenRegistrationScreen.emit()
            }
        }
    }

    private fun initCommands() {
        ch.hideKeyboard.bindTo { requireActivity().hideKeyboard() }
        ch.showMessage.bindTo { mc.show(it) }
    }

    private fun clearFocus() {
        with(binding) {
            loginTf.clearFocus()
            passwordTf.clearFocus()
        }
    }
}
