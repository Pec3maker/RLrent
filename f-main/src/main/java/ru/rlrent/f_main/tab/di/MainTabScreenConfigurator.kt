package ru.rlrent.f_main.tab.di

import android.os.Bundle
import dagger.Component
import dagger.Module
import dagger.Provides
import ru.rlrent.f_main.tab.*
import ru.rlrent.f_main.tab.MainTabEvent
import ru.rlrent.f_main.tab.MainTabFragmentView
import ru.rlrent.f_main.tab.MainTabMiddleware
import ru.rlrent.f_main.tab.MainTabReducer
import ru.rlrent.f_main.tab.MainTabStateHolder
import ru.surfstudio.android.core.mvi.impls.event.hub.ScreenEventHub
import ru.surfstudio.android.core.mvi.impls.event.hub.dependency.ScreenEventHubDependency
import ru.surfstudio.android.core.mvi.impls.ui.binder.ScreenBinder
import ru.surfstudio.android.core.mvi.impls.ui.binder.ScreenBinderDependency
import ru.surfstudio.android.core.mvp.configurator.BindableScreenComponent
import ru.surfstudio.android.core.mvp.configurator.ScreenComponent
import ru.surfstudio.android.dagger.scope.PerScreen
import ru.surfstudio.practice.f_main.tab.*
import ru.surfstudio.practice.ui.activity.di.ActivityComponent
import ru.surfstudio.practice.ui.activity.di.FragmentScreenConfigurator
import ru.surfstudio.practice.ui.navigation.routes.MainTabRoute
import ru.surfstudio.practice.ui.screen_modules.CustomScreenModule
import ru.surfstudio.practice.ui.screen_modules.FragmentScreenModule

/**
 * [MainTabFragmentView]
 */
internal class MainTabScreenConfigurator(arguments: Bundle?) : FragmentScreenConfigurator(arguments) {

    @PerScreen
    @Component(
            dependencies = [ActivityComponent::class],
            modules = [FragmentScreenModule::class, MainTabScreenModule::class]
    )
    internal interface MainTabScreenComponent : BindableScreenComponent<MainTabFragmentView>

    @Module
    internal class MainTabScreenModule(route: MainTabRoute) : CustomScreenModule<MainTabRoute>(route) {

        @Provides
        @PerScreen
        fun provideEventHub(
                screenEventHubDependency: ScreenEventHubDependency
        ) = ScreenEventHub<MainTabEvent>(screenEventHubDependency, MainTabEvent::Lifecycle)

        @Provides
        @PerScreen
        fun provideBinder(
            screenBinderDependency: ScreenBinderDependency,
            eventHub: ScreenEventHub<MainTabEvent>,
            mw: MainTabMiddleware,
            sh: MainTabStateHolder,
            reducer: MainTabReducer
        ): Any = ScreenBinder(screenBinderDependency).apply {
            bind(eventHub, mw, sh, reducer)
        }
    }

    override fun createScreenComponent(
            parentComponent: ActivityComponent?,
            fragmentScreenModule: FragmentScreenModule?,
            args: Bundle?
    ): ScreenComponent<*> {
        return DaggerMainTabScreenConfigurator_MainTabScreenComponent.builder()
                .activityComponent(parentComponent)
                .fragmentScreenModule(fragmentScreenModule)
                .mainTabScreenModule(MainTabScreenModule(MainTabRoute()))
                .build()
    }
}
