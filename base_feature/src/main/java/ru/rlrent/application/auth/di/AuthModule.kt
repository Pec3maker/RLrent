package ru.rlrent.application.auth.di

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.rlrent.i_auth.AuthApi
import ru.surfstudio.android.dagger.scope.PerApplication
import javax.inject.Named

@Module
class AuthModule {

    @Provides
    @PerApplication
    internal fun provideAuthApi(
        @Named("auth") retrofit: Retrofit
    ): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }
}
