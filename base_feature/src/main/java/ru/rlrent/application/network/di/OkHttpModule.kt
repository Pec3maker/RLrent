package ru.rlrent.application.network.di

import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import ru.rlrent.i_auth.token.RefreshSessionInterceptor
import ru.rlrent.i_network.network.InvalidSessionListener
import ru.rlrent.i_network.service.ServiceInterceptor
import ru.rlrent.i_session.SessionChangedInteractor
import ru.rlrent.i_token.TokenStorage
import ru.surfstudio.android.dagger.scope.PerApplication
import java.util.concurrent.TimeUnit
import javax.inject.Named

const val DI_NAME_SERVICE_INTERCEPTOR = "DI_NAME_SERVICE_INTERCEPTOR"
private const val NETWORK_TIMEOUT = 30L // sec

/**
 * этот модуль вынесен отдельно для возможности замены его при интеграционном тестировании
 */
@Module
class OkHttpModule {

    @Provides
    @PerApplication
    @Named(DI_NAME_SERVICE_INTERCEPTOR)
    internal fun provideServiceInterceptor(tokenStorage: TokenStorage): Interceptor {
        return ServiceInterceptor(tokenStorage)
    }

    @Provides
    @PerApplication
    internal fun provideOkHttpClient(
        @Named(DI_NAME_SERVICE_INTERCEPTOR) serviceInterceptor: Interceptor,
        cacheInterceptor: ru.rlrent.i_network.network.cache.SimpleCacheInterceptor,
        etagInterceptor: ru.rlrent.i_network.network.etag.EtagInterceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor,
        refreshSessionInterceptor: RefreshSessionInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder().apply {
            connectTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)

            addInterceptor(cacheInterceptor)
            addInterceptor(etagInterceptor)
            addInterceptor(serviceInterceptor)
            addInterceptor(httpLoggingInterceptor)
            addInterceptor(refreshSessionInterceptor)
        }.build()
    }

    @Provides
    @PerApplication
    internal fun provideInvalidSessionListener(sessionChangedInteractor: SessionChangedInteractor): InvalidSessionListener {
        return sessionChangedInteractor
    }
}
