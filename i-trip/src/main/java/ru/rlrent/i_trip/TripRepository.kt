package ru.rlrent.i_trip

import io.reactivex.Single
import ru.rlrent.domain.trip.Trip
import ru.rlrent.i_network.network.transform
import ru.rlrent.i_network.network.transformCollection
import ru.rlrent.i_network.service.BaseNetworkService
import ru.surfstudio.android.dagger.scope.PerApplication
import javax.inject.Inject

/**
 * Сервис, отвечающий за поездки
 */
@PerApplication
class TripRepository @Inject constructor(
    private val tripApi: TripApi
) : BaseNetworkService() {

    /**
     * Начать поездку
     */
    fun startTrip(tripId: Int): Single<Trip> = tripApi.startTrip(tripId).transform()

    /**
     * Закончить поездку
     */
    fun finishTrip(): Single<Trip> = tripApi.finishTrip().transform()

    /**
     * Загрузить поездки
     */
    fun getTrips(): Single<List<Trip>> = tripApi.getTrips().transformCollection()
}
