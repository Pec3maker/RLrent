package ru.rlrent.f_profile.di

import android.os.Bundle
import dagger.Component
import dagger.Module
import dagger.Provides
import ru.rlrent.f_profile.*
import ru.rlrent.f_profile.ProfileEvent
import ru.rlrent.f_profile.ProfileFragmentView
import ru.rlrent.f_profile.ProfileMiddleware
import ru.rlrent.f_profile.ProfileReducer
import ru.rlrent.f_profile.ProfileScreenStateHolder
import ru.rlrent.ui.activity.di.ActivityComponent
import ru.rlrent.ui.activity.di.FragmentScreenConfigurator
import ru.rlrent.ui.navigation.routes.ProfileFragmentRoute
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
 * Конфигуратор профиля [ProfileFragmentView]
 */
internal class ProfileScreenConfigurator(arguments: Bundle?) :
    FragmentScreenConfigurator(arguments) {

    @PerScreen
    @Component(
        dependencies = [ActivityComponent::class],
        modules = [FragmentScreenModule::class, ProfileScreenModule::class]
    )
    internal interface ProfileScreenComponent : BindableScreenComponent<ProfileFragmentView>

    @Module
    internal class ProfileScreenModule(route: ProfileFragmentRoute) :
        CustomScreenModule<ProfileFragmentRoute>(route) {

        @Provides
        @PerScreen
        fun provideEventHub(
            screenEventHubDependency: ScreenEventHubDependency
        ) = ScreenEventHub<ProfileEvent>(screenEventHubDependency, ProfileEvent::Lifecycle)

        @Provides
        @PerScreen
        fun provideBinder(
            screenBinderDependency: ScreenBinderDependency,
            eventHub: ScreenEventHub<ProfileEvent>,
            mw: ProfileMiddleware,
            sh: ProfileScreenStateHolder,
            reducer: ProfileReducer
        ): Any = ScreenBinder(screenBinderDependency).apply {
            bind(eventHub, mw, sh, reducer)
        }
    }

    override fun createScreenComponent(
        parentComponent: ActivityComponent?,
        fragmentScreenModule: FragmentScreenModule?,
        args: Bundle
    ): ScreenComponent<*> {
        return DaggerProfileScreenConfigurator_ProfileScreenComponent.builder()
            .activityComponent(parentComponent)
            .fragmentScreenModule(fragmentScreenModule)
            .profileScreenModule(ProfileScreenModule(ProfileFragmentRoute(args)))
            .build()
    }
}
