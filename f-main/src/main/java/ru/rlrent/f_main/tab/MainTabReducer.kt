package ru.rlrent.f_main.tab

import ru.surfstudio.android.core.mvi.impls.ui.reactor.BaseReactorDependency
import ru.surfstudio.android.core.mvi.impls.ui.reducer.BaseReducer
import ru.surfstudio.android.core.mvp.binding.rx.relation.mvp.State
import ru.surfstudio.android.dagger.scope.PerScreen
import ru.rlrent.domain.users.User
import ru.rlrent.f_main.tab.MainTabEvent.ChangePhvState
import ru.rlrent.f_main.tab.MainTabEvent.Input
import ru.surfstudio.practice.i_storage.UserStorage
import ru.surfstudio.practice.ui.navigation.routes.MainTabType
import ru.surfstudio.practice.ui.placeholder.LoadStateType
import javax.inject.Inject

internal data class MainTabState(
    val selectedTab: MainTabType = MainTabType.PROJECTS,
    val currentUser: User = User(),
    val phvState: LoadStateType = LoadStateType.None()
)

@PerScreen
internal class MainTabStateHolder @Inject constructor(
    userStorage: UserStorage
) : State<MainTabState>(MainTabState(currentUser = userStorage.currentUser ?: User()))

@PerScreen
internal class MainTabReducer @Inject constructor(
    dependency: BaseReactorDependency
) : BaseReducer<MainTabEvent, MainTabState>(dependency) {

    override fun reduce(state: MainTabState, event: MainTabEvent): MainTabState {
        return when (event) {
            is Input.TabSelected -> state.copy(
                selectedTab = MainTabType.getByValue(
                    event.position
                )
            )
            is ChangePhvState -> onChangePhvState(state, event)
            else -> state
        }
    }

    private fun onChangePhvState(state: MainTabState, event: ChangePhvState): MainTabState {
        return state.copy(
            phvState = if (event.isLoading) LoadStateType.Transparent else LoadStateType.None()
        )
    }
}
