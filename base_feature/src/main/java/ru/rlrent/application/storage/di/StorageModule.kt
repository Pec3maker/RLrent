package ru.rlrent.application.storage.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.surfstudio.android.dagger.scope.PerApplication
import ru.surfstudio.android.filestorage.naming.NamingProcessor
import ru.surfstudio.android.filestorage.utils.AppDirectoriesProvider
import ru.rlrent.i_storage.theme.DefaultThemeTypeStorage
import ru.rlrent.i_storage.theme.ThemeTypeStorage

@Module
class StorageModule {

    @Provides
    @PerApplication
    fun provideDefaultNamingProcessor(): NamingProcessor = NamingProcessor { rawName -> rawName }

    @Provides
    @PerApplication
    internal fun provideThemeTypeStorage(
        context: Context,
        namingProcessor: NamingProcessor
    ): ThemeTypeStorage {
        return DefaultThemeTypeStorage(
            AppDirectoriesProvider.provideNoBackupStorageDir(context),
            namingProcessor
        )
    }
}
