package ru.rlrent.f_map

import io.reactivex.Observable
import ru.rlrent.f_map.MapEvent.Navigation
import ru.rlrent.ui.mvi.navigation.base.NavigationMiddleware
import ru.surfstudio.android.core.mvi.impls.ui.middleware.BaseMiddleware
import ru.surfstudio.android.core.mvi.impls.ui.middleware.BaseMiddlewareDependency
import ru.surfstudio.android.dagger.scope.PerScreen
import javax.inject.Inject

@PerScreen
internal class MapMiddleware @Inject constructor(
    private val sh: MapScreenStateHolder,
    basePresenterDependency: BaseMiddlewareDependency,
    private val navigationMiddleware: NavigationMiddleware
) : BaseMiddleware<MapEvent>(basePresenterDependency) {

    private val state: MapState
        get() = sh.value

    override fun transform(eventStream: Observable<MapEvent>): Observable<out MapEvent> =
        transformations(eventStream) {
            addAll(
                Navigation::class decomposeTo navigationMiddleware,
            )
        }
}
