package ru.rlrent.application.network.di

import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import ru.rlrent.i_auth.token.AuthRefreshSessionInterceptor
import ru.rlrent.i_auth.token.TransportRefreshSessionInterceptor
import ru.rlrent.i_auth.token.TripRefreshSessionInterceptor
import ru.rlrent.i_network.network.InvalidSessionListener
import ru.rlrent.i_network.network.cache.SimpleCacheInterceptor
import ru.rlrent.i_network.network.etag.EtagInterceptor
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
    @Named("AuthClient")
    internal fun provideAuthOkHttpClient(
        @Named(DI_NAME_SERVICE_INTERCEPTOR) serviceInterceptor: Interceptor,
        @Named("AuthSimpleCacheInterceptor") cacheInterceptor: SimpleCacheInterceptor,
        etagInterceptor: EtagInterceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor,
        refreshSessionInterceptor: AuthRefreshSessionInterceptor,
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
    @Named("TripClient")
    internal fun provideTripOkHttpClient(
        @Named(DI_NAME_SERVICE_INTERCEPTOR) serviceInterceptor: Interceptor,
        @Named("TransportSimpleCacheInterceptor") cacheInterceptor: SimpleCacheInterceptor,
        etagInterceptor: EtagInterceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor,
        refreshSessionInterceptor: TripRefreshSessionInterceptor,
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
    @Named("TransportClient")
    internal fun provideTransportOkHttpClient(
        @Named(DI_NAME_SERVICE_INTERCEPTOR) serviceInterceptor: Interceptor,
        @Named("TransportSimpleCacheInterceptor") cacheInterceptor: SimpleCacheInterceptor,
        etagInterceptor: EtagInterceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor,
        refreshSessionInterceptor: TransportRefreshSessionInterceptor,
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
