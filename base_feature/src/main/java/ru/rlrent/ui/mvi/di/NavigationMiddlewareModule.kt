package ru.rlrent.ui.mvi.di

import dagger.Module
import dagger.Provides
import ru.rlrent.ui.mvi.navigation.AppNavigationMiddleware
import ru.rlrent.ui.mvi.navigation.base.NavigationMiddleware
import ru.surfstudio.android.core.mvi.impls.ui.freezer.LifecycleSubscriptionFreezer
import ru.surfstudio.android.dagger.scope.PerScreen
import ru.surfstudio.android.navigation.executor.NavigationCommandExecutor

/**
 * Модуль, который провайдит Middleware для навигации.
 */
@Module
class NavigationMiddlewareModule {

    @Provides
    @PerScreen
    fun provideNavigationMiddleware(
            navigationCommandExecutor: NavigationCommandExecutor,
            freezer: LifecycleSubscriptionFreezer
    ): NavigationMiddleware {
        return AppNavigationMiddleware(
                navigationCommandExecutor,
                freezer
        )
    }
}
