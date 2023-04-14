package ru.rlrent.application.storage.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.surfstudio.android.dagger.scope.PerApplication
import ru.surfstudio.android.filestorage.naming.NamingProcessor
import ru.surfstudio.android.filestorage.utils.AppDirectoriesProvider
import ru.surfstudio.practice.i_storage.DefaultUserStorage
import ru.surfstudio.practice.i_storage.UserStorage

@Module
class UserStorageModule {

    @Provides
    @PerApplication
    fun provideDefaultNamingProcessor(): NamingProcessor = NamingProcessor { rawName -> rawName }

    @Provides
    @PerApplication
    internal fun provideUserStorage(
        context: Context,
        namingProcessor: NamingProcessor
    ): UserStorage {
        return DefaultUserStorage(
            AppDirectoriesProvider.provideNoBackupStorageDir(context),
            namingProcessor
        )
    }
}
