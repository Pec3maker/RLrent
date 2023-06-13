package ru.rlrent.i_trip

import android.annotation.SuppressLint
import io.reactivex.Single
import ru.rlrent.domain.trip.Trip
import ru.rlrent.i_network.network.BaseNetworkInteractor
import ru.surfstudio.android.connection.ConnectionProvider
import ru.surfstudio.android.dagger.scope.PerApplication
import javax.inject.Inject

/**
 * Интерактор, отвечающий за поездки
 */
@PerApplication
@SuppressLint("CheckResult")
class TripInteractor @Inject constructor(
    connectionQualityProvider: ConnectionProvider,
    private val tripRepository: TripRepository,
) : BaseNetworkInteractor(connectionQualityProvider) {

    /**
     * Начать поездку
     */
    fun startTrip(tripId: Int): Single<Trip> = tripRepository.startTrip(tripId)

    /**
     * Закончить поездку
     */
    fun finishTrip(): Single<Trip> = tripRepository.finishTrip()
}
