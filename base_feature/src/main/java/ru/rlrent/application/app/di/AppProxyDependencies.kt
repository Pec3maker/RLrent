package ru.rlrent.application.app.di

import android.content.Context
import android.content.SharedPreferences
import ru.rlrent.i_auth.AuthInteractor
import ru.rlrent.i_initialization.InitializeAppInteractor
import ru.rlrent.i_session.SessionChangedInteractor
import ru.rlrent.ui.mvi.navigation.IntentChecker
import ru.rlrent.ui.permissions.AppPermissionManager
import ru.surfstudio.android.activity.holder.ActiveActivityHolder
import ru.surfstudio.android.connection.ConnectionProvider
import ru.surfstudio.android.core.ui.navigation.activity.navigator.GlobalNavigator
import ru.surfstudio.android.core.ui.provider.resource.ResourceProvider
import ru.surfstudio.android.dagger.scope.PerApplication
import ru.surfstudio.android.navigation.executor.AppCommandExecutor
import ru.surfstudio.android.navigation.observer.ScreenResultObserver
import ru.surfstudio.android.navigation.provider.ActivityNavigationProvider
import ru.surfstudio.android.navigation.provider.callbacks.ActivityNavigationProviderCallbacks
import ru.surfstudio.android.rx.extension.scheduler.SchedulersProvider
import ru.surfstudio.android.shared.pref.NO_BACKUP_SHARED_PREF
import javax.inject.Named

/**
 * Интерфейс, объединяющий в себе все зависимости в скоупе [PerApplication]
 * Следует использовать в компоненте Application и других компонентах более высоких уровней,
 * зависящих от него.
 */
interface AppProxyDependencies {
    fun initializeAppInteractor(): InitializeAppInteractor
    fun context(): Context
    fun activeActivityHolder(): ActiveActivityHolder
    fun connectionProvider(): ConnectionProvider
    fun sessionChangedInteractor(): SessionChangedInteractor
    fun schedulerProvider(): SchedulersProvider
    fun resourceProvider(): ResourceProvider
    fun globalNavigator(): GlobalNavigator
    fun intentChecker(): IntentChecker

    fun commandExecutor(): AppCommandExecutor
    fun navigationCallbacks(): ActivityNavigationProviderCallbacks
    fun activityNavigationProvider(): ActivityNavigationProvider
    fun screenResultObserver(): ScreenResultObserver
    fun permissionManager(): AppPermissionManager

    @Named(NO_BACKUP_SHARED_PREF)
    fun sharedPreferences(): SharedPreferences

    fun authInteractor(): AuthInteractor
}
