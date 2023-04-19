package ru.rlrent.f_auth.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.WindowInsetsCompat.Type.systemBars
import ru.android.rlrent.f_auth.R
import ru.android.rlrent.f_auth.databinding.FragmentAuthBinding
import ru.rlrent.f_auth.auth.AuthEvent.Input
import ru.rlrent.f_auth.auth.di.AuthScreenConfigurator
import ru.rlrent.ui.mvi.view.BaseMviFragmentView
import ru.rlrent.ui.util.onKeyboardChanges
import ru.rlrent.ui.util.paddingTo
import ru.rlrent.ui.util.setSystemBarColor
import ru.rlrent.ui.util.updateMargin
import ru.rlrent.v_message_controller_top.IconMessageController
import ru.surfstudio.android.core.mvi.impls.event.hub.ScreenEventHub
import ru.surfstudio.android.core.ui.navigation.feature.route.feature.CrossFeatureFragment
import ru.surfstudio.android.core.ui.view_binding.viewBinding
import javax.inject.Inject

/**
 * Вью аутентификации
 */
internal class AuthFragmentView :
    BaseMviFragmentView<AuthState, AuthEvent>(),
    CrossFeatureFragment {
    @Inject
    override lateinit var hub: ScreenEventHub<AuthEvent>

    @Inject
    override lateinit var sh: AuthScreenStateHolder

    @Inject
    lateinit var ch: AuthCommandHolder

    @Inject
    lateinit var mc: IconMessageController

    private val binding: FragmentAuthBinding by viewBinding(FragmentAuthBinding::bind)

    override fun createConfigurator() = AuthScreenConfigurator(arguments)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_auth, container, false)
    }

    override fun render(state: AuthState) {
    }

    override fun initInsets() {
        val activity = requireActivity()
        activity.setSystemBarColor(false)

        with(binding) {
            activity.onKeyboardChanges { height ->
                policyTv.updateMargin(bottom = height)
            }
            root paddingTo systemBars()
        }
    }

    override fun initViews() {
        initCommands()

        with(binding) {
            loginTf.setOnNextButton {
                passwordTf.editText.requestFocus()
            }
            passwordTf.setOnDoneButton {
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
        ch.showMessage.bindTo { mc.show(it) }
    }
}
