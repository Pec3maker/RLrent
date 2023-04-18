package ru.rlrent.ui.activity.di

import android.app.Activity
import android.content.Intent
import ru.rlrent.application.app.di.AppComponent
import ru.rlrent.application.app.di.AppInjector
import ru.rlrent.ui.screen_modules.ActivityScreenModule
import ru.surfstudio.android.core.mvp.configurator.BaseActivityViewConfigurator

/**
 * Базовый конфигуратор для экрана, основанного на [Activity]
 */
abstract class ActivityScreenConfigurator(
    intent: Intent
) : BaseActivityViewConfigurator<AppComponent, ActivityComponent, ActivityScreenModule>(intent) {

    override fun createActivityComponent(parentComponent: AppComponent): ActivityComponent {
        return DaggerActivityComponent.builder()
            .appComponent(parentComponent)
            .activityModule(ActivityModule(persistentScope))
            .build()
    }

    override fun getParentComponent(): AppComponent {
        return AppInjector.appComponent
    }

    override fun getActivityScreenModule(): ActivityScreenModule {
        return ActivityScreenModule(persistentScope)
    }
}