package ru.rlrent.f_registration.confirm_password.di

import android.os.Bundle
import dagger.Component
import dagger.Module
import dagger.Provides
import ru.rlrent.f_registration.confirm_password.*
import ru.rlrent.ui.activity.di.ActivityComponent
import ru.rlrent.ui.activity.di.FragmentScreenConfigurator
import ru.rlrent.ui.navigation.routes.RegistrationPasswordFragmentRoute
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
 * Конфигуратор регистрации [RegistrationPasswordFragmentView]
 */
internal class RegistrationPasswordScreenConfigurator(arguments: Bundle?) :
    FragmentScreenConfigurator(arguments) {

    @PerScreen
    @Component(
        dependencies = [ActivityComponent::class],
        modules = [FragmentScreenModule::class, RegistrationPasswordScreenModule::class]
    )
    internal interface RegistrationPasswordScreenComponent :
        BindableScreenComponent<RegistrationPasswordFragmentView>

    @Module
    internal class RegistrationPasswordScreenModule(route: RegistrationPasswordFragmentRoute) :
        CustomScreenModule<RegistrationPasswordFragmentRoute>(route) {

        @Provides
        @PerScreen
        fun provideEventHub(
            screenEventHubDependency: ScreenEventHubDependency
        ) = ScreenEventHub<RegistrationPasswordEvent>(
            screenEventHubDependency,
            RegistrationPasswordEvent::Lifecycle
        )

        @Provides
        @PerScreen
        fun provideBinder(
            screenBinderDependency: ScreenBinderDependency,
            eventHub: ScreenEventHub<RegistrationPasswordEvent>,
            mw: RegistrationPasswordMiddleware,
            sh: RegistrationPasswordScreenStateHolder,
            reducer: RegistrationPasswordReducer
        ): Any = ScreenBinder(screenBinderDependency).apply {
            bind(eventHub, mw, sh, reducer)
        }
    }

    override fun createScreenComponent(
        parentComponent: ActivityComponent?,
        fragmentScreenModule: FragmentScreenModule?,
        args: Bundle
    ): ScreenComponent<*> {
        return DaggerRegistrationPasswordScreenConfigurator_RegistrationPasswordScreenComponent.builder()
            .activityComponent(parentComponent)
            .fragmentScreenModule(fragmentScreenModule)
            .registrationPasswordScreenModule(
                RegistrationPasswordScreenModule(RegistrationPasswordFragmentRoute(args))
            )
            .build()
    }
}
