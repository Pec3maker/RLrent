package ru.rlrent.ui.dialog.choose_bill_amount

import android.os.Bundle
import dagger.Component
import dagger.Module
import dagger.Provides
import ru.rlrent.ui.activity.di.ActivityComponent
import ru.rlrent.ui.activity.di.DialogScreenConfigurator
import ru.rlrent.ui.navigation.routes.ChooseBillAmountDialogRoute
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
 * Конфигуратор диалога с оплатой
 * */
class ChooseBillAmountConfigurator(args: Bundle?) :
    DialogScreenConfigurator(args ?: Bundle.EMPTY) {

    @PerScreen
    @Component(
        dependencies = [ActivityComponent::class],
        modules = [FragmentScreenModule::class, ChooseBillAmountScreenModule::class]
    )
    internal interface ChooseBillAmountScreenComponent :
        BindableScreenComponent<ChooseBillAmountFragment>

    @Module
    internal class ChooseBillAmountScreenModule(route: ChooseBillAmountDialogRoute) :
        CustomScreenModule<ChooseBillAmountDialogRoute>(route) {

        @Provides
        @PerScreen
        fun provideEventHub(
            screenEventHubDependency: ScreenEventHubDependency
        ) = ScreenEventHub<ChooseBillAmountEvent>(
            screenEventHubDependency,
            ChooseBillAmountEvent::Lifecycle
        )

        @Provides
        @PerScreen
        fun provideBinder(
            screenBinderDependency: ScreenBinderDependency,
            eventHub: ScreenEventHub<ChooseBillAmountEvent>,
            mw: ChooseBillAmountMiddleware,
            sh: ChooseBillAmountStateHolder,
            reducer: ChooseBillAmountReducer
        ): Any = ScreenBinder(screenBinderDependency).apply {
            bind(eventHub, mw, sh, reducer)
        }
    }

    override fun createScreenComponent(
        parentComponent: ActivityComponent?,
        fragmentScreenModule: FragmentScreenModule?,
        args: Bundle
    ): ScreenComponent<*> {
        return DaggerChooseBillAmountConfigurator_ChooseBillAmountScreenComponent.builder()
            .activityComponent(parentComponent)
            .fragmentScreenModule(fragmentScreenModule)
            .chooseBillAmountScreenModule(
                ChooseBillAmountScreenModule(
                    ChooseBillAmountDialogRoute(
                        args
                    )
                )
            )
            .build()
    }
}
