package ru.rlrent.application.network.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.surfstudio.android.dagger.scope.PerApplication
import ru.surfstudio.android.filestorage.utils.AppDirectoriesProvider
import ru.rlrent.i_network.network.etag.EtagInterceptor
import ru.rlrent.i_network.network.etag.storage.EtagStorage
import ru.rlrent.i_network.network.etag.storage.EtagCache

@Module
class EtagModule {

    @Provides
    @PerApplication
    internal fun provideEtagCache(context: Context): ru.rlrent.i_network.network.etag.storage.EtagCache {
        return ru.rlrent.i_network.network.etag.storage.EtagCache(
            AppDirectoriesProvider.provideNoBackupStorageDir(
                context
            )
        )
    }

    @Provides
    @PerApplication
    internal fun provideEtagStorage(etagCache: ru.rlrent.i_network.network.etag.storage.EtagCache): ru.rlrent.i_network.network.etag.storage.EtagStorage {
        return ru.rlrent.i_network.network.etag.storage.EtagStorage(etagCache)
    }

    @Provides
    @PerApplication
    internal fun provideEtagInterceptor(etagStorage: ru.rlrent.i_network.network.etag.storage.EtagStorage): ru.rlrent.i_network.network.etag.EtagInterceptor {
        return ru.rlrent.i_network.network.etag.EtagInterceptor(etagStorage)
    }
}