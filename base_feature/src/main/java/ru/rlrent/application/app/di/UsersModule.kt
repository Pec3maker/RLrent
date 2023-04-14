package ru.rlrent.application.app.di

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.surfstudio.android.dagger.scope.PerApplication
import ru.surfstudio.practice.i_users.UsersApi

@Module
class UsersModule {

    @Provides
    @PerApplication
    internal fun provideUsersApi(retrofit: Retrofit): UsersApi {
        return retrofit.create(UsersApi::class.java)
    }
}
