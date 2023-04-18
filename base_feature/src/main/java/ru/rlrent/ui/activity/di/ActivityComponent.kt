package ru.rlrent.ui.activity.di

import dagger.Component
import ru.rlrent.application.app.di.AppComponent
import ru.rlrent.application.app.di.AppProxyDependencies
import ru.surfstudio.android.dagger.scope.PerActivity

/**
 * Компонент для @[PerActivity] скоупа
 */
@PerActivity
@Component(
    dependencies = [AppComponent::class],
    modules = [ActivityModule::class]
)
interface ActivityComponent : AppProxyDependencies, ActivityProxyDependencies