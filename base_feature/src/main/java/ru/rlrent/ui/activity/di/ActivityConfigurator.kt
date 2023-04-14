package ru.rlrent.ui.activity.di

import android.content.Context
import ru.surfstudio.android.core.ui.configurator.BaseActivityConfigurator
import ru.surfstudio.practice.application.app.di.AppComponent
import ru.surfstudio.practice.application.app.di.AppInjector

class ActivityConfigurator(
        val context: Context
) : BaseActivityConfigurator<ActivityComponent, AppComponent>() {

    override fun createActivityComponent(parentComponent: AppComponent?): ActivityComponent =
            ru.rlrent.practice.ui.activity.di.DaggerActivityComponent.builder()
                    .appComponent(parentComponent)
                    .build()

    override fun getParentComponent(): AppComponent = AppInjector.appComponent
}