package ru.rlrent.f_auth.auth.di

import android.os.Bundle
import dagger.Component
import dagger.Module
import dagger.Provides
import ru.rlrent.f_auth.auth.AuthEvent
import ru.rlrent.f_auth.auth.AuthFragmentView
import ru.rlrent.f_auth.auth.AuthMiddleware
import ru.rlrent.f_auth.auth.AuthReducer
import ru.rlrent.f_auth.auth.AuthScreenStateHolder
import ru.rlrent.ui.activity.di.ActivityComponent
import ru.rlrent.ui.activity.di.FragmentScreenConfigurator
import ru.rlrent.ui.navigation.routes.AuthFragmentRoute
import ru.rlrent.ui.screen_modules.CustomScreenModule
import ru.rlrent.ui.screen_modules.FragmentScreenModule
import ru.surfstudio.android.core.mvi.impls.event.hub.ScreenEventHub
import ru.surfstudio.android.core.mvi.impls.event.hub.dependency.ScreenEventHubDependency
import ru.surfstudio.android.core.mvi.impls.ui.binder.ScreenBinder
import ru.surfstudio.android.core.mvi.impls.ui.binder.ScreenBinderDependency
import ru.surfstudio.android.core.mvp.configurator.BindableScreenComponent
import ru.surfstudio.android.core.mvp.configurator.ScreenComponent
import ru.surfstudio.android.dagger.scope.PerScreen

/**
 * Конфигуратор аутентификации [AuthFragmentView]
 */
internal class AuthScreenConfigurator(arguments: Bundle?) : FragmentScreenConfigurator(arguments) {

    @PerScreen
    @Component(
        dependencies = [ActivityComponent::class],
        modules = [FragmentScreenModule::class, AuthScreenModule::class]
    )
    internal interface AuthScreenComponent : BindableScreenComponent<AuthFragmentView>

    @Module
    internal class AuthScreenModule(route: AuthFragmentRoute) :
        CustomScreenModule<AuthFragmentRoute>(route) {

        @Provides
        @PerScreen
        fun provideEventHub(
            screenEventHubDependency: ScreenEventHubDependency
        ) = ScreenEventHub<AuthEvent>(screenEventHubDependency, AuthEvent::Lifecycle)

        @Provides
        @PerScreen
        fun provideBinder(
            screenBinderDependency: ScreenBinderDependency,
            eventHub: ScreenEventHub<AuthEvent>,
            mw: AuthMiddleware,
            sh: AuthScreenStateHolder,
            reducer: AuthReducer
        ): Any = ScreenBinder(screenBinderDependency).apply {
            bind(eventHub, mw, sh, reducer)
        }
    }

    override fun createScreenComponent(
        parentComponent: ActivityComponent?,
        fragmentScreenModule: FragmentScreenModule?,
        args: Bundle?
    ): ScreenComponent<*> {
        return DaggerAuthScreenConfigurator_AuthScreenComponent.builder()
            .activityComponent(parentComponent)
            .fragmentScreenModule(fragmentScreenModule)
            .authScreenModule(AuthScreenModule(AuthFragmentRoute()))
            .build()
    }
}
