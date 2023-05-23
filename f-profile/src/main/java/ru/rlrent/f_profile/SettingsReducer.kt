package ru.rlrent.f_profile

import ru.rlrent.domain.user.User
import ru.rlrent.ui.navigation.routes.ProfileFragmentRoute
import ru.surfstudio.android.core.mvi.impls.ui.reactor.BaseReactorDependency
import ru.surfstudio.android.core.mvi.impls.ui.reducer.BaseReducer
import ru.surfstudio.android.core.mvp.binding.rx.relation.mvp.State
import ru.surfstudio.android.dagger.scope.PerScreen
import javax.inject.Inject

/**
 * State [ProfileFragmentView]
 */
internal data class ProfileState(
    val user: User
)

/**
 * State Holder [ProfileFragmentView]
 */
@PerScreen
internal class ProfileScreenStateHolder @Inject constructor(
    route: ProfileFragmentRoute
) :
    State<ProfileState>(ProfileState(user = route.user))

/**
 * Command Holder [ProfileFragmentView]
 */
@PerScreen
internal class ProfileCommandHolder @Inject constructor()

/**
 * Reducer [ProfileFragmentView]
 */
@PerScreen
internal class ProfileReducer @Inject constructor(
    dependency: BaseReactorDependency,
    private val ch: ProfileCommandHolder
) : BaseReducer<ProfileEvent, ProfileState>(dependency) {

    override fun reduce(state: ProfileState, event: ProfileEvent): ProfileState {
        return state
    }
}
