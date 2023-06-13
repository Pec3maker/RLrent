package ru.rlrent.f_registration.name.di

import android.os.Bundle
import dagger.Component
import dagger.Module
import dagger.Provides
import ru.rlrent.f_registration.name.*
import ru.rlrent.ui.activity.di.ActivityComponent
import ru.rlrent.ui.activity.di.FragmentScreenConfigurator
import ru.rlrent.ui.navigation.routes.RegistrationNameFragmentRoute
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
 * Конфигуратор регистрации [RegistrationNameFragmentView]
 */
internal class RegistrationNameScreenConfigurator(arguments: Bundle?) :
    FragmentScreenConfigurator(arguments) {

    @PerScreen
    @Component(
        dependencies = [ActivityComponent::class],
        modules = [FragmentScreenModule::class, RegistrationNameScreenModule::class]
    )
    internal interface RegistrationNameScreenComponent :
        BindableScreenComponent<RegistrationNameFragmentView>

    @Module
    internal class RegistrationNameScreenModule(route: RegistrationNameFragmentRoute) :
        CustomScreenModule<RegistrationNameFragmentRoute>(route) {

        @Provides
        @PerScreen
        fun provideEventHub(
            screenEventHubDependency: ScreenEventHubDependency
        ) = ScreenEventHub<RegistrationNameEvent>(
            screenEventHubDependency,
            RegistrationNameEvent::Lifecycle
        )

        @Provides
        @PerScreen
        fun provideBinder(
            screenBinderDependency: ScreenBinderDependency,
            eventHub: ScreenEventHub<RegistrationNameEvent>,
            mw: RegistrationNameMiddleware,
            sh: RegistrationNameScreenStateHolder,
            reducer: RegistrationNameReducer
        ): Any = ScreenBinder(screenBinderDependency).apply {
            bind(eventHub, mw, sh, reducer)
        }
    }

    override fun createScreenComponent(
        parentComponent: ActivityComponent?,
        fragmentScreenModule: FragmentScreenModule?,
        args: Bundle?
    ): ScreenComponent<*> {
        return DaggerRegistrationNameScreenConfigurator_RegistrationNameScreenComponent.builder()
            .activityComponent(parentComponent)
            .fragmentScreenModule(fragmentScreenModule)
            .registrationNameScreenModule(RegistrationNameScreenModule(RegistrationNameFragmentRoute()))
            .build()
    }
}
