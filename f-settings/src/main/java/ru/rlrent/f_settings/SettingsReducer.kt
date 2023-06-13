package ru.rlrent.f_settings

import ru.rlrent.f_settings.SettingsEvent.Lifecycle
import ru.rlrent.i_storage.theme.ThemeChangedInteractor
import ru.rlrent.i_storage.theme.ThemeType
import ru.surfstudio.android.core.mvi.impls.ui.reactor.BaseReactorDependency
import ru.surfstudio.android.core.mvi.impls.ui.reducer.BaseReducer
import ru.surfstudio.android.core.mvp.binding.rx.relation.mvp.State
import ru.surfstudio.android.core.ui.state.LifecycleStage
import ru.surfstudio.android.dagger.scope.PerScreen
import javax.inject.Inject

/**
 * State [SettingsFragmentView]
 */
internal data class SettingsState(
    val isChecked: Boolean = false
)

/**
 * State Holder [SettingsFragmentView]
 */
@PerScreen
internal class SettingsScreenStateHolder @Inject constructor() :
    State<SettingsState>(SettingsState())

/**
 * Command Holder [SettingsFragmentView]
 */
@PerScreen
internal class SettingsCommandHolder @Inject constructor()

/**
 * Reducer [SettingsFragmentView]
 */
@PerScreen
internal class SettingsReducer @Inject constructor(
    dependency: BaseReactorDependency,
    private val ch: SettingsCommandHolder,
    private val themeChangedInteractor: ThemeChangedInteractor
) : BaseReducer<SettingsEvent, SettingsState>(dependency) {

    override fun reduce(state: SettingsState, event: SettingsEvent): SettingsState {
        return when (event) {
            is Lifecycle -> handleOnCreate(state, event)
            else -> state
        }
    }

    private fun handleOnCreate(
        state: SettingsState,
        event: Lifecycle
    ): SettingsState {
        if (event.stage != LifecycleStage.CREATED) return state

        return state.copy(isChecked = themeChangedInteractor.currentTheme == ThemeType.DARK)
    }
}
