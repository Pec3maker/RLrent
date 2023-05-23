package ru.rlrent.application.app.di

import dagger.Component
import ru.rlrent.application.auth.di.AuthModule
import ru.rlrent.application.cache.di.CacheModule
import ru.rlrent.application.migration.di.MigrationModule
import ru.rlrent.application.network.di.EtagModule
import ru.rlrent.application.network.di.NetworkModule
import ru.rlrent.application.network.di.OkHttpModule
import ru.rlrent.application.storage.di.SharedPrefModule
import ru.rlrent.application.storage.di.StorageModule
import ru.rlrent.ui.navigation.di.NavigationModule
import ru.surfstudio.android.dagger.scope.PerApplication

@PerApplication
@Component(
    modules = [
        AppModule::class,
        MigrationModule::class,
        SharedPrefModule::class,
        StorageModule::class,
        AuthModule::class,
        CacheModule::class,
        EtagModule::class,
        NetworkModule::class,
        OkHttpModule::class,
        NavigationModule::class,
    ]
)
interface AppComponent : AppProxyDependencies
