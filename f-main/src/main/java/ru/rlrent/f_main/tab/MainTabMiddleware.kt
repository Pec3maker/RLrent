package ru.rlrent.f_main.tab

import io.reactivex.Observable
import ru.surfstudio.android.core.mvi.impls.ui.middleware.BaseMiddleware
import ru.surfstudio.android.core.mvi.impls.ui.middleware.BaseMiddlewareDependency
import ru.surfstudio.android.dagger.scope.PerScreen
import ru.surfstudio.android.navigation.command.fragment.base.FragmentNavigationCommand
import ru.surfstudio.android.rxbus.RxBus
import ru.rlrent.f_main.tab.MainTabEvent.*
import ru.surfstudio.practice.ui.mvi.navigation.base.NavigationMiddleware
import ru.surfstudio.practice.ui.mvi.navigation.extension.replace
import ru.surfstudio.practice.ui.navigation.routes.ProfileFragmentRoute
import ru.surfstudio.practice.ui.rxbus_events.MainTabPlaceholderLoadingRxBusEvent
import javax.inject.Inject

@PerScreen
internal class MainTabMiddleware @Inject constructor(
    basePresenterDependency: BaseMiddlewareDependency,
    sh: MainTabStateHolder,
    private val navigationMiddleware: NavigationMiddleware,
    private val rxBus: RxBus
) : BaseMiddleware<MainTabEvent>(basePresenterDependency) {

    private val state = sh.value

    override fun transform(eventStream: Observable<MainTabEvent>): Observable<out MainTabEvent> =
        transformations(eventStream) {
            addAll(
                Navigation::class decomposeTo navigationMiddleware,
                Input.UserInfoClicked::class mapTo ::onUserInfoClicked,
                observePlaceholderLoading()
            )
        }

    private fun observePlaceholderLoading(): Observable<out MainTabEvent> {
        return rxBus.observeEvents(MainTabPlaceholderLoadingRxBusEvent::class.java)
            .map { ChangePhvState(isLoading = it.isLoading) }
    }

    private fun onUserInfoClicked(event: Input.UserInfoClicked): MainTabEvent {
        return Navigation().replace(
            ProfileFragmentRoute(state.currentUser, true),
            sourceTag = FragmentNavigationCommand.ACTIVITY_NAVIGATION_TAG
        )
    }
}
