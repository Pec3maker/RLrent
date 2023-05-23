package ru.rlrent.f_map

import io.reactivex.Observable
import ru.android.rlrent.f_map.BuildConfig
import ru.rlrent.domain.user.User
import ru.rlrent.f_map.MapEvent.Input
import ru.rlrent.f_map.MapEvent.Navigation
import ru.rlrent.ui.mvi.navigation.base.NavigationMiddleware
import ru.rlrent.ui.mvi.navigation.extension.replace
import ru.rlrent.ui.mvi.navigation.extension.start
import ru.rlrent.ui.navigation.routes.OpenUrlRoute
import ru.rlrent.ui.navigation.routes.ProfileFragmentRoute
import ru.rlrent.ui.navigation.routes.SettingsFragmentRoute
import ru.surfstudio.android.core.mvi.impls.ui.middleware.BaseMiddleware
import ru.surfstudio.android.core.mvi.impls.ui.middleware.BaseMiddlewareDependency
import ru.surfstudio.android.dagger.scope.PerScreen
import ru.surfstudio.android.navigation.command.fragment.base.FragmentNavigationCommand
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
                Input.SettingsClicked::class mapTo ::navigateToSettings,
                Input.InfoClicked::class mapTo ::navigateToInfo,
                Input.ProfileClicked::class mapTo ::navigateToProfile
            )
        }

    private fun navigateToProfile(event: Input.ProfileClicked): MapEvent {
        return Navigation().replace(
            ProfileFragmentRoute(
                user = User(
                    imageUrl = "https://static.mk.ru/upload/entities/2023/01/28/00/articles/detailPicture/7e/7b/d4/49/b68a5fe5f0019d636eb8d941e06e5a8c.jpg",
                    firstName = "Ваня",
                    email = "vanya@gmail.com",
                    tripsCost = 1550,
                    tripsCount = 10,
                    registrationDate = "12.02.2023",
                    phoneNumber = "+7 989 832 68 89",
                    bill = "1200"
                )
            ),
            sourceTag = FragmentNavigationCommand.ACTIVITY_NAVIGATION_TAG
        )
    }

    private fun navigateToSettings(event: Input.SettingsClicked): MapEvent {
        return Navigation().replace(
            SettingsFragmentRoute(),
            sourceTag = FragmentNavigationCommand.ACTIVITY_NAVIGATION_TAG
        )
    }

    private fun navigateToInfo(event: Input.InfoClicked): MapEvent {
        return Navigation().start(OpenUrlRoute(BuildConfig.POLICY_URL))
    }
}
