package ru.rlrent.f_auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.WindowInsetsCompat.Type.ime
import androidx.core.view.WindowInsetsCompat.Type.systemBars
import ru.android.rlrent.f_auth.R
import ru.android.rlrent.f_auth.databinding.FragmentAuthBinding
import ru.rlrent.f_auth.di.AuthScreenConfigurator
import ru.rlrent.ui.mvi.view.BaseMviFragmentView
import ru.rlrent.ui.placeholder.LoadStateView
import ru.rlrent.ui.placeholder.loadstate.renderer.DefaultLoadStateRenderer
import ru.rlrent.ui.util.hideKeyboardOutsideEditTextTouch
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

    override fun initViews() {
        initCommands()
        hideKeyboardOutsideEditTextTouch()

        with(binding) {
            loginTf.setOnNextButton {
                passwordTf.editText.requestFocus()
                scrollView.scrollTo(0, root.bottom)
            }
            passwordTf.setOnDoneButton {
                AuthEvent.Input.SignIn(loginTf.text, passwordTf.text).emit()
            }
            enterBtn.setOnClickListener {
                AuthEvent.Input.SignIn(loginTf.text, passwordTf.text).emit()
            }

            root.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
                if (isKeyboardVisible()) scrollView.scrollTo(0, root.bottom)
            }
        }
    }

    override fun initInsets(): InsetsApi {
        return InsetsApiBuilder()
            .addInsetMargin(binding.authView, systemBars())
            .addInsetMargin(binding.authView, ime())
            .build()
    }

    private fun initCommands() {
        ch.hideKeyboard.bindTo { hideKeyboard() }
        ch.showMessage.bindTo { mc.show(it) }
    }

    private fun clearFocus() {
        with(binding) {
            loginTf.clearFocus()
            passwordTf.clearFocus()
        }
    }
}
