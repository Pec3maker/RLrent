package ru.rlrent.application.cache.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.rlrent.i_network.network.cache.SimpleCacheFactory
import ru.rlrent.i_network.network.cache.SimpleCacheInterceptor
import ru.rlrent.i_network.network.cache.SimpleCacheUrlConnector
import ru.surfstudio.android.dagger.scope.PerApplication
import ru.surfstudio.android.filestorage.utils.AppDirectoriesProvider
import javax.inject.Named

/**
 * Dagger-модуль для удовлетворения зависимостей классов, использующихся для кэширования
 */
@Module
class CacheModule {

    @Provides
    @PerApplication
    @Named("AuthSimpleCacheInterceptor")
    internal fun provideAuthSimpleCacheInterceptor(
        @Named("AuthSimpleCacheFactory") simpleCacheFactory: SimpleCacheFactory,
        @Named("AuthSimpleCacheConnector") simpleCacheUrlConnector: SimpleCacheUrlConnector
    ): SimpleCacheInterceptor {
        return SimpleCacheInterceptor(
            simpleCacheFactory,
            simpleCacheUrlConnector
        )
    }

    @Provides
    @PerApplication
    @Named("TripSimpleCacheInterceptor")
    internal fun provideTripSimpleCacheInterceptor(
        @Named("TripSimpleCacheFactory") simpleCacheFactory: SimpleCacheFactory,
        @Named("TripSimpleCacheConnector") simpleCacheUrlConnector: SimpleCacheUrlConnector
    ): SimpleCacheInterceptor {
        return SimpleCacheInterceptor(
            simpleCacheFactory,
            simpleCacheUrlConnector
        )
    }

    @Provides
    @PerApplication
    @Named("TransportSimpleCacheInterceptor")
    internal fun provideTransportSimpleCacheInterceptor(
        @Named("TransportSimpleCacheFactory") simpleCacheFactory: SimpleCacheFactory,
        @Named("TransportSimpleCacheConnector") simpleCacheUrlConnector: SimpleCacheUrlConnector
    ): SimpleCacheInterceptor {
        return SimpleCacheInterceptor(
            simpleCacheFactory,
            simpleCacheUrlConnector
        )
    }

    @Provides
    @PerApplication
    @Named("AuthSimpleCacheFactory")
    internal fun provideAuthSimpleCacheFactory(
        context: Context,
        @Named("AuthSimpleCacheConnector") cacheUrlConnector: SimpleCacheUrlConnector
    ): SimpleCacheFactory {
        return SimpleCacheFactory(
            AppDirectoriesProvider.provideBackupStorageDir(
                context
            ), cacheUrlConnector
        )
    }

    @Provides
    @PerApplication
    @Named("TripSimpleCacheFactory")
    internal fun provideTripSimpleCacheFactory(
        context: Context,
        @Named("TripSimpleCacheConnector") cacheUrlConnector: SimpleCacheUrlConnector
    ): SimpleCacheFactory {
        return SimpleCacheFactory(
            AppDirectoriesProvider.provideBackupStorageDir(
                context
            ), cacheUrlConnector
        )
    }

    @Provides
    @PerApplication
    @Named("TransportSimpleCacheFactory")
    internal fun provideTransportSimpleCacheFactory(
        context: Context,
        @Named("TransportSimpleCacheConnector") cacheUrlConnector: SimpleCacheUrlConnector
    ): SimpleCacheFactory {
        return SimpleCacheFactory(
            AppDirectoriesProvider.provideBackupStorageDir(
                context
            ), cacheUrlConnector
        )
    }

    @Provides
    @PerApplication
    @Named("AuthSimpleCacheConnector")
    internal fun providesAuthSimpleCacheConnector(
        @Named("authUrl") baseUrl: ru.rlrent.i_network.network.BaseUrl,
        simpleCacheInfoStorage: ru.rlrent.i_network.cache.SimpleCacheInfoStorage
    ): SimpleCacheUrlConnector {
        return SimpleCacheUrlConnector(
            baseUrl,
            simpleCacheInfoStorage.simpleCaches
        )
    }

    @Provides
    @PerApplication
    @Named("TripSimpleCacheConnector")
    internal fun providesTripSimpleCacheConnector(
        @Named("tripUrl") baseUrl: ru.rlrent.i_network.network.BaseUrl,
        simpleCacheInfoStorage: ru.rlrent.i_network.cache.SimpleCacheInfoStorage
    ): SimpleCacheUrlConnector {
        return SimpleCacheUrlConnector(
            baseUrl,
            simpleCacheInfoStorage.simpleCaches
        )
    }

    @Provides
    @PerApplication
    @Named("TransportSimpleCacheConnector")
    internal fun providesTransportSimpleCacheConnector(
        @Named("transportUrl") baseUrl: ru.rlrent.i_network.network.BaseUrl,
        simpleCacheInfoStorage: ru.rlrent.i_network.cache.SimpleCacheInfoStorage
    ): SimpleCacheUrlConnector {
        return SimpleCacheUrlConnector(
            baseUrl,
            simpleCacheInfoStorage.simpleCaches
        )
    }
}