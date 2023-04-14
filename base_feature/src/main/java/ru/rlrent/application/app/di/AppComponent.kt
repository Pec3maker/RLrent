package ru.rlrent.application.app.di

import dagger.Component
import ru.surfstudio.android.dagger.scope.PerApplication
import ru.surfstudio.practice.application.auth.di.AuthModule
import ru.surfstudio.practice.application.cache.di.CacheModule
import ru.surfstudio.practice.application.migration.di.MigrationModule
import ru.surfstudio.practice.application.network.di.EtagModule
import ru.surfstudio.practice.application.network.di.NetworkModule
import ru.surfstudio.practice.application.network.di.OkHttpModule
import ru.surfstudio.practice.application.projects.di.ProjectsModule
import ru.surfstudio.practice.application.storage.di.SharedPrefModule
import ru.surfstudio.practice.application.storage.di.UserStorageModule
import ru.surfstudio.practice.ui.navigation.di.NavigationModule

@PerApplication
@Component(
    modules = [
        AppModule::class,
        MigrationModule::class,
        SharedPrefModule::class,
        UserStorageModule::class,
        AuthModule::class,
        UsersModule::class,
        ProjectsModule::class,
        CacheModule::class,
        EtagModule::class,
        NetworkModule::class,
        OkHttpModule::class,
        NavigationModule::class,
    ]
)
interface AppComponent : AppProxyDependencies
