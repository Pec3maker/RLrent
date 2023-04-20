package ru.rlrent.f_map

import ru.surfstudio.android.core.mvi.impls.ui.reactor.BaseReactorDependency
import ru.surfstudio.android.core.mvi.impls.ui.reducer.BaseReducer
import ru.surfstudio.android.core.mvp.binding.rx.relation.mvp.State
import ru.surfstudio.android.dagger.scope.PerScreen
import javax.inject.Inject

/**
 * State [MapFragmentView]
 */
internal class MapState

/**
 * State Holder [MapFragmentView]
 */
@PerScreen
internal class MapScreenStateHolder @Inject constructor() : State<MapState>(MapState())

/**
 * Command Holder [MapFragmentView]
 */
@PerScreen
internal class MapCommandHolder @Inject constructor() {
}

/**
 * Reducer [MapFragmentView]
 */
@PerScreen
internal class MapReducer @Inject constructor(
    dependency: BaseReactorDependency,
    private val ch: MapCommandHolder
) : BaseReducer<MapEvent, MapState>(dependency) {

    override fun reduce(state: MapState, event: MapEvent): MapState {
        return state
    }
}
