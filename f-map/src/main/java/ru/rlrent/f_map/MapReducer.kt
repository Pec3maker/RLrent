package ru.rlrent.f_map

import ru.rlrent.domain.transport.*
import ru.rlrent.f_map.MapEvent.Input
import ru.surfstudio.android.core.mvi.impls.ui.reactor.BaseReactorDependency
import ru.surfstudio.android.core.mvi.impls.ui.reducer.BaseReducer
import ru.surfstudio.android.core.mvp.binding.rx.relation.mvp.State
import ru.surfstudio.android.dagger.scope.PerScreen
import javax.inject.Inject

/**
 * State [MapFragmentView]
 */
data class MapState(
    val availableTransport: List<Transport> = listOf(
        Transport(
            1,
            "ABC123",
            TransportStatus.FREE,
            TransportCondition.GOOD,
            TransportType.ELECTRIC_SCOOTER,
            "Xiaomi",
            TransportPosition(47.205096, 38.935508),
            20,
            80
        ),
        Transport(
            2,
            "DEF456",
            TransportStatus.FREE,
            TransportCondition.GOOD,
            TransportType.ELECTRIC_SCOOTER,
            "Ninebot",
            TransportPosition(47.200845, 38.923123),
            20,
            100
        ),
        Transport(
            3,
            "GHI789",
            TransportStatus.FREE,
            TransportCondition.GOOD,
            TransportType.ELECTRIC_SCOOTER,
            "Segway",
            TransportPosition(47.216577, 38.918840),
            25,
            50
        ),
        Transport(
            4,
            "JKL012",
            TransportStatus.FREE,
            TransportCondition.GOOD,
            TransportType.ELECTRIC_SCOOTER,
            "Xiaomi",
            TransportPosition(47.218196, 38.922550),
            25,
            60
        ),
        Transport(
            5,
            "MNO345",
            TransportStatus.FREE,
            TransportCondition.GOOD,
            TransportType.ELECTRIC_SCOOTER,
            "Ninebot",
            TransportPosition(47.217178, 38.925939),
            15,
            40
        ),
        Transport(
            6,
            "PQR678",
            TransportStatus.FREE,
            TransportCondition.GOOD,
            TransportType.ELECTRIC_SCOOTER,
            "Segway",
            TransportPosition(47.214950, 38.919501),
            20,
            70
        ),
        Transport(
            7,
            "STU901",
            TransportStatus.FREE,
            TransportCondition.GOOD,
            TransportType.ELECTRIC_SCOOTER,
            "Xiaomi",
            TransportPosition(47.211891, 38.919305),
            25,
            80
        ),
        Transport(
            8,
            "VWX234",
            TransportStatus.FREE,
            TransportCondition.GOOD,
            TransportType.ELECTRIC_SCOOTER,
            "Ninebot",
            TransportPosition(47.216288, 38.922391),
            15,
            30
        ),
        Transport(
            9,
            "YZA567",
            TransportStatus.FREE,
            TransportCondition.GOOD,
            TransportType.ELECTRIC_SCOOTER,
            "Segway",
            TransportPosition(47.218137, 38.918364),
            20,
            90
        ),
        Transport(
            10,
            "BCD890",
            TransportStatus.FREE,
            TransportCondition.GOOD,
            TransportType.ELECTRIC_SCOOTER,
            "Xiaomi",
            TransportPosition(47.216576, 38.918854),
            25,
            100
        ),
        Transport(
            11,
            "EFG123",
            TransportStatus.FREE,
            TransportCondition.GOOD,
            TransportType.ELECTRIC_SCOOTER,
            "Ninebot",
            TransportPosition(47.215860, 38.919649),
            15,
            40
        ),
        Transport(
            12,
            "HIJ456",
            TransportStatus.FREE,
            TransportCondition.GOOD,
            TransportType.ELECTRIC_SCOOTER,
            "Segway",
            TransportPosition(47.214182, 38.920258),
            20,
            50
        ),
        Transport(
            13,
            "KLM789",
            TransportStatus.FREE,
            TransportCondition.GOOD,
            TransportType.ELECTRIC_SCOOTER,
            "Xiaomi",
            TransportPosition(47.215603, 38.922677),
            20,
            70
        ),
        Transport(
            15,
            "QRS345",
            TransportStatus.FREE,
            TransportCondition.GOOD,
            TransportType.BICYCLE,
            "Giant",
            TransportPosition(47.201058, 38.924283),
            30,
            0
        ),
        Transport(
            16,
            "TUV678",
            TransportStatus.FREE,
            TransportCondition.GOOD,
            TransportType.BICYCLE,
            "Specialized",
            TransportPosition(47.205688, 38.935267),
            25,
            0
        ),
        Transport(
            17,
            "WXY901",
            TransportStatus.FREE,
            TransportCondition.GOOD,
            TransportType.BICYCLE,
            "Trek",
            TransportPosition(47.206860, 38.936107),
            20,
            0
        ),
        Transport(
            18,
            "ZAB234",
            TransportStatus.FREE,
            TransportCondition.GOOD,
            TransportType.BICYCLE,
            "Cannondale",
            TransportPosition(47.209732, 38.926033),
            30,
            0
        ),
        Transport(
            19,
            "CDE567",
            TransportStatus.FREE,
            TransportCondition.GOOD,
            TransportType.BICYCLE,
            "Giant",
            TransportPosition(47.210818, 38.919718),
            25,
            0
        ),
        Transport(
            20,
            "FGH890",
            TransportStatus.FREE,
            TransportCondition.GOOD,
            TransportType.BICYCLE,
            "Specialized",
            TransportPosition(47.213357, 38.915975),
            20,
            0
        ),
        Transport(
            21,
            "IJK123",
            TransportStatus.FREE,
            TransportCondition.GOOD,
            TransportType.BICYCLE,
            "Trek",
            TransportPosition(47.212508, 38.920009),
            30,
            0
        ),
        Transport(
            22,
            "LMN456",
            TransportStatus.FREE,
            TransportCondition.GOOD,
            TransportType.BICYCLE,
            "Cannondale",
            TransportPosition(47.218319, 38.912528),
            25,
            0
        ),
        Transport(
            23,
            "OPQ789",
            TransportStatus.FREE,
            TransportCondition.GOOD,
            TransportType.BICYCLE,
            "Giant",
            TransportPosition(47.217010, 38.913660),
            20,
            0
        ),
        Transport(
            24,
            "RST012",
            TransportStatus.FREE,
            TransportCondition.GOOD,
            TransportType.BICYCLE,
            "Specialized",
            TransportPosition(47.212839, 38.917103),
            30,
            0
        ),
        Transport(
            25,
            "TUV345",
            TransportStatus.FREE,
            TransportCondition.GOOD,
            TransportType.BICYCLE,
            "Trek",
            TransportPosition(47.216298, 38.910354),
            25,
            0
        ),
        Transport(
            26,
            "WXY678",
            TransportStatus.FREE,
            TransportCondition.GOOD,
            TransportType.BICYCLE,
            "Cannondale",
            TransportPosition(47.215559, 38.916128),
            20,
            0
        ),
        Transport(
            27,
            "ZAB901",
            TransportStatus.FREE,
            TransportCondition.GOOD,
            TransportType.BICYCLE,
            "Giant",
            TransportPosition(47.211299, 38.915010),
            30,
            0
        ),
        Transport(
            28,
            "CDE234",
            TransportStatus.FREE,
            TransportCondition.GOOD,
            TransportType.BICYCLE,
            "Specialized",
            TransportPosition(47.213489, 38.916501),
            25,
            0
        )
    ),
    val userLat: Double = 0.0,
    val userLong: Double = 0.0
)

/**
 * State Holder [MapFragmentView]
 */
@PerScreen
internal class MapScreenStateHolder @Inject constructor() : State<MapState>(MapState())

/**
 * Command Holder [MapFragmentView]
 */
@PerScreen
internal class MapCommandHolder @Inject constructor() {
}

/**
 * Reducer [MapFragmentView]
 */
@PerScreen
internal class MapReducer @Inject constructor(
    dependency: BaseReactorDependency,
    private val ch: MapCommandHolder
) : BaseReducer<MapEvent, MapState>(dependency) {

    override fun reduce(state: MapState, event: MapEvent): MapState {
        return when (event) {
            is Input.UpdateUserLocation -> handleUpdateUserLocation(event, state)
            else -> state
        }
    }

    private fun handleUpdateUserLocation(
        event: Input.UpdateUserLocation,
        state: MapState
    ): MapState {
        return state.copy(userLat = event.lat, userLong = event.long)
    }
}
