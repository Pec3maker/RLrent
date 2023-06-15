package ru.rlrent.f_profile

import ru.rlrent.domain.user.User
import ru.rlrent.f_profile.ProfileEvent.GetUser
import ru.rlrent.ui.mvi.mappers.RequestMappers
import ru.rlrent.ui.mvi.placeholder.LoadStateType
import ru.rlrent.ui.navigation.routes.ProfileFragmentRoute
import ru.rlrent.ui.util.placeholderState
import ru.surfstudio.android.core.mvi.impls.ui.reactor.BaseReactorDependency
import ru.surfstudio.android.core.mvi.impls.ui.reducer.BaseReducer
import ru.surfstudio.android.core.mvi.ui.mapper.RequestMapper
import ru.surfstudio.android.core.mvp.binding.rx.relation.mvp.State
import ru.surfstudio.android.dagger.scope.PerScreen
import javax.inject.Inject

/**
 * State [ProfileFragmentView]
 */
internal data class ProfileState(
    val user: User,
    val placeholderState: LoadStateType = LoadStateType.None()
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
        return when (event) {
            is GetUser -> handleGetUserRequest(state, event)
            else -> state
        }
    }

    private fun handleGetUserRequest(state: ProfileState, event: GetUser): ProfileState {
        val userRequest = RequestMapper.builder(event.request)
            .mapData(RequestMappers.data.default())
            .mapLoading(RequestMappers.loading.default())
            .handleError(RequestMappers.error.loadingBased(errorHandler))
            .build()

        return state.copy(
            placeholderState = userRequest.placeholderState,
            user = userRequest.data ?: User()
        )
    }
}