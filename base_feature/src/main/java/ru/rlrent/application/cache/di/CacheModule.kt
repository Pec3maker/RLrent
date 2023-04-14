package ru.rlrent.application.cache.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.surfstudio.android.dagger.scope.PerApplication
import ru.surfstudio.android.filestorage.utils.AppDirectoriesProvider
import ru.rlrent.i_network.network.BaseUrl
import ru.rlrent.i_network.network.cache.SimpleCacheFactory
import ru.rlrent.i_network.network.cache.SimpleCacheInterceptor
import ru.rlrent.i_network.network.cache.SimpleCacheUrlConnector
import ru.rlrent.i_network.cache.SimpleCacheInfoStorage

/**
 * Dagger-модуль для удовлетворения зависимостей классов, использующихся для кэширования
 */
@Module
class CacheModule {

    @Provides
    @PerApplication
    internal fun provideSimpleCacheInterceptor(
        simpleCacheFactory: ru.rlrent.i_network.network.cache.SimpleCacheFactory,
        simpleCacheUrlConnector: ru.rlrent.i_network.network.cache.SimpleCacheUrlConnector
    ): ru.rlrent.i_network.network.cache.SimpleCacheInterceptor {
        return ru.rlrent.i_network.network.cache.SimpleCacheInterceptor(
            simpleCacheFactory,
            simpleCacheUrlConnector
        )
    }

    @Provides
    @PerApplication
    internal fun provideSimpleCacheFactory(
            context: Context,
            cacheUrlConnector: ru.rlrent.i_network.network.cache.SimpleCacheUrlConnector
    ): ru.rlrent.i_network.network.cache.SimpleCacheFactory {
        return ru.rlrent.i_network.network.cache.SimpleCacheFactory(
            AppDirectoriesProvider.provideBackupStorageDir(
                context
            ), cacheUrlConnector
        )
    }

    @Provides
    @PerApplication
    internal fun providesSimpleCacheConnector(
        baseUrl: ru.rlrent.i_network.network.BaseUrl,
        simpleCacheInfoStorage: ru.rlrent.i_network.cache.SimpleCacheInfoStorage
    ): ru.rlrent.i_network.network.cache.SimpleCacheUrlConnector {
        return ru.rlrent.i_network.network.cache.SimpleCacheUrlConnector(
            baseUrl,
            simpleCacheInfoStorage.simpleCaches
        )
    }
}