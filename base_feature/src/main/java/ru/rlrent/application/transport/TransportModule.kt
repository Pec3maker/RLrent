package ru.rlrent.application.transport

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.rlrent.i_transport.TransportApi
import ru.surfstudio.android.dagger.scope.PerApplication
import javax.inject.Named

@Module
class TransportModule {

    @Provides
    @PerApplication
    internal fun provideTransportApi(
        @Named("transport") retrofit: Retrofit
    ): TransportApi {
        return retrofit.create(TransportApi::class.java)
    }
}