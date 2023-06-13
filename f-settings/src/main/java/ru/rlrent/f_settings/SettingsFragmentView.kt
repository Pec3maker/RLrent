package ru.rlrent.f_settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.WindowInsetsCompat.Type.navigationBars
import androidx.core.view.WindowInsetsCompat.Type.statusBars
import com.jakewharton.rxbinding2.view.clicks
import ru.android.rlrent.f_settings.R
import ru.android.rlrent.f_settings.databinding.FragmentSettingsBinding
import ru.rlrent.f_settings.SettingsEvent.Input
import ru.rlrent.f_settings.SettingsEvent.Input.SetDarkTheme
import ru.rlrent.f_settings.SettingsEvent.Input.SetLightTheme
import ru.rlrent.f_settings.di.SettingsScreenConfigurator
import ru.rlrent.ui.mvi.view.BaseMviFragmentView
import ru.rlrent.ui.util.addDefaultOnBackPressedCallback
import ru.rlrent.ui.util.paddingTo
import ru.rlrent.v_message_controller_top.IconMessageController
import ru.surfstudio.android.core.mvi.impls.event.hub.ScreenEventHub
import ru.surfstudio.android.core.ui.navigation.feature.route.feature.CrossFeatureFragment
import ru.surfstudio.android.core.ui.view_binding.viewBinding
import javax.inject.Inject

/**
 * Вью настроек
 */
internal class SettingsFragmentView :
    BaseMviFragmentView<SettingsState, SettingsEvent>(),
    CrossFeatureFragment {
    @Inject
    override lateinit var hub: ScreenEventHub<SettingsEvent>

    @Inject
    override lateinit var sh: SettingsScreenStateHolder

    @Inject
    lateinit var ch: SettingsCommandHolder

    @Inject
    lateinit var mc: IconMessageController

    private val binding: FragmentSettingsBinding by viewBinding(FragmentSettingsBinding::bind)

    override fun createConfigurator() = SettingsScreenConfigurator(arguments)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun initInsets() {
        with(binding) {
            settingsContainer paddingTo statusBars()
            root paddingTo navigationBars()
        }
    }

    override fun initViews() {
        addDefaultOnBackPressedCallback { Input.BackClicked.emit() }
        with(binding) {
            leftNavigationArrow.clicks().emit(Input.BackClicked)
            themeSwitch.setOnClickListener {
                if (themeSwitch.isChecked) {
                    SetDarkTheme.emit()
                } else {
                    SetLightTheme.emit()
                }
            }
        }
    }

    override fun render(state: SettingsState) {
        binding.themeSwitch.isChecked = state.isChecked
    }
}
