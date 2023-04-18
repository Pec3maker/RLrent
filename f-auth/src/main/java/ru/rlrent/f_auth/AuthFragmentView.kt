package ru.rlrent.f_auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.android.rlrent.f_auth.R
import ru.android.rlrent.f_auth.databinding.FragmentAuthBinding
import ru.rlrent.f_auth.di.AuthScreenConfigurator
import ru.rlrent.ui.mvi.view.BaseMviFragmentView
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

    override fun initViews() {
        initCommands()

    }

    private fun initCommands() {
        ch.showMessage.bindTo { mc.show(it) }
    }
}
