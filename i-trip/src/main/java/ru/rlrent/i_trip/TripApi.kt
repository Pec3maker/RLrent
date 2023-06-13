package ru.rlrent.i_trip

import io.reactivex.Single
import retrofit2.http.POST
import retrofit2.http.Path
import ru.rlrent.i_network.generated.urls.TripUrls.TRIP_FINISH_PATH
import ru.rlrent.i_network.generated.urls.TripUrls.TRIP_START_PATH
import ru.rlrent.i_trip.dto.TripResponse

/**
 * Api для поездок
 */
interface TripApi {

    @POST("$TRIP_START_PATH/{id}")
    fun startTrip(@Path("id") tripId: Int): Single<TripResponse>

    @POST(TRIP_FINISH_PATH)
    fun finishTrip(): Single<TripResponse>
}
