package ru.rlrent.f_settings

import io.reactivex.Observable
import ru.rlrent.f_settings.SettingsEvent.Input
import ru.rlrent.f_settings.SettingsEvent.Navigation
import ru.rlrent.i_storage.theme.ThemeChangedInteractor
import ru.rlrent.ui.mvi.navigation.base.NavigationMiddleware
import ru.rlrent.ui.mvi.navigation.extension.removeLast
import ru.surfstudio.android.core.mvi.impls.ui.middleware.BaseMiddleware
import ru.surfstudio.android.core.mvi.impls.ui.middleware.BaseMiddlewareDependency
import ru.surfstudio.android.dagger.scope.PerScreen
import javax.inject.Inject

@PerScreen
internal class SettingsMiddleware @Inject constructor(
    private val sh: SettingsScreenStateHolder,
    basePresenterDependency: BaseMiddlewareDependency,
    private val navigationMiddleware: NavigationMiddleware,
    private val themeChangedInteractor: ThemeChangedInteractor
) : BaseMiddleware<SettingsEvent>(basePresenterDependency) {

    private val state: SettingsState
        get() = sh.value

    override fun transform(eventStream: Observable<SettingsEvent>): Observable<out SettingsEvent> =
        transformations(eventStream) {
            addAll(
                Navigation::class decomposeTo navigationMiddleware,
                Input.SetDarkTheme::class reactTo ::setDarkTheme,
                Input.SetLightTheme::class reactTo ::setLightTheme,
                Input.BackClicked::class mapTo { navigateToPreviousScreen() }
            )
        }

    private fun setLightTheme(event: Input.SetLightTheme) {
        themeChangedInteractor.setLightTheme()
    }

    private fun setDarkTheme(event: Input.SetDarkTheme) {
        themeChangedInteractor.setDarkTheme()
    }

    private fun navigateToPreviousScreen(): Navigation {
        return Navigation().removeLast()
    }
}
