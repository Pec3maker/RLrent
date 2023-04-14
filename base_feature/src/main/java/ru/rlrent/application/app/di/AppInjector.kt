package ru.rlrent.application.app.di

import ru.surfstudio.practice.application.app.App

/**
 * Объект ответственный за создание и хранение [AppComponent]
 */
object AppInjector {

    lateinit var appComponent: AppComponent

    fun initInjector(app: App) {
        appComponent = ru.rlrent.practice.application.app.di.DaggerAppComponent.builder()
                .appModule(AppModule(app, app.activeActivityHolder))
                .build()
    }
}