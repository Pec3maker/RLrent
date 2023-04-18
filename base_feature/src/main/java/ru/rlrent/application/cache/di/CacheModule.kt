package ru.rlrent.application.cache.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.rlrent.i_network.network.cache.SimpleCacheFactory
import ru.rlrent.i_network.network.cache.SimpleCacheInterceptor
import ru.rlrent.i_network.network.cache.SimpleCacheUrlConnector
import ru.surfstudio.android.dagger.scope.PerApplication
import ru.surfstudio.android.filestorage.utils.AppDirectoriesProvider

/**
 * Dagger-модуль для удовлетворения зависимостей классов, использующихся для кэширования
 */
@Module
class CacheModule {

    @Provides
    @PerApplication
    internal fun provideSimpleCacheInterceptor(
        simpleCacheFactory: SimpleCacheFactory,
        simpleCacheUrlConnector: SimpleCacheUrlConnector
    ): SimpleCacheInterceptor {
        return SimpleCacheInterceptor(
            simpleCacheFactory,
            simpleCacheUrlConnector
        )
    }

    @Provides
    @PerApplication
    internal fun provideSimpleCacheFactory(
        context: Context,
        cacheUrlConnector: SimpleCacheUrlConnector
    ): SimpleCacheFactory {
        return SimpleCacheFactory(
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
    ): SimpleCacheUrlConnector {
        return SimpleCacheUrlConnector(
            baseUrl,
            simpleCacheInfoStorage.simpleCaches
        )
    }
}