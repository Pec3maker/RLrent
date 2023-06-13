package ru.rlrent.application.trip

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.rlrent.i_trip.TripApi
import ru.surfstudio.android.dagger.scope.PerApplication
import javax.inject.Named

@Module
class TripModule {

    @Provides
    @PerApplication
    internal fun provideTransportApi(
        @Named("trip") retrofit: Retrofit
    ): TripApi {
        return retrofit.create(TripApi::class.java)
    }
}