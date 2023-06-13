package ru.rlrent.ui.dialog.base

import ru.rlrent.application.app.di.AppComponent
import ru.rlrent.application.app.di.AppInjector
import ru.surfstudio.android.navigation.executor.NavigationCommandExecutor
import ru.surfstudio.android.navigation.observer.command.EmitScreenResult

/**
 * Базовый простой диалог с поддержкой возвращения результата и навигации.
 *
 * Имеет доступ к Dagger-компоненту приложения [AppComponent] через метод [getAppComponent]
 * Может вернуть результат своей работы через метод [NavigationCommandExecutor.execute]
 * и передачу в него команды [EmitScreenResult].
 */
interface CoreDialog {

    fun getAppComponent(): AppComponent = AppInjector.appComponent

    fun getCommandExecutor(): NavigationCommandExecutor = getAppComponent().commandExecutor()
}
