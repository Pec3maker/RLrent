package ru.rlrent.f_map

import android.annotation.SuppressLint
import android.graphics.PointF
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding2.view.clicks
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.RequestPoint
import com.yandex.mapkit.RequestPointType
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.layers.ObjectEvent
import com.yandex.mapkit.map.*
import com.yandex.mapkit.transport.TransportFactory
import com.yandex.mapkit.transport.masstransit.Route
import com.yandex.mapkit.transport.masstransit.Session
import com.yandex.mapkit.transport.masstransit.TimeOptions
import com.yandex.mapkit.user_location.UserLocationLayer
import com.yandex.mapkit.user_location.UserLocationObjectListener
import com.yandex.mapkit.user_location.UserLocationView
import com.yandex.runtime.image.ImageProvider
import ru.android.rlrent.f_map.R
import ru.android.rlrent.f_map.databinding.FragmentMapBinding
import ru.rlrent.domain.transport.TransportType
import ru.rlrent.f_map.MapEvent.Input.*
import ru.rlrent.f_map.di.MapScreenConfigurator
import ru.rlrent.ui.mvi.view.BaseMviFragmentView
import ru.rlrent.ui.util.slideInFromLeftIf
import ru.rlrent.v_message_controller_top.IconMessageController
import ru.surfstudio.android.core.mvi.impls.event.hub.ScreenEventHub
import ru.surfstudio.android.core.ui.navigation.feature.route.feature.CrossFeatureFragment
import ru.surfstudio.android.core.ui.view_binding.viewBinding
import javax.inject.Inject


/**
 * Вью карты
 */
internal class MapFragmentView :
    BaseMviFragmentView<MapState, MapEvent>(),
    CrossFeatureFragment,
    UserLocationObjectListener,
    Session.RouteListener {

    @Inject
    override lateinit var hub: ScreenEventHub<MapEvent>

    @Inject
    override lateinit var sh: MapScreenStateHolder

    @Inject
    lateinit var ch: MapCommandHolder

    @Inject
    lateinit var mc: IconMessageController

    private val binding: FragmentMapBinding by viewBinding(FragmentMapBinding::bind)

    private lateinit var userLocationLayer: UserLocationLayer
    private var polylineMapObject: PolylineMapObject? = null
    private var mapObjectTapListeners: MutableList<MapObjectTapListener> = mutableListOf()

    override fun createConfigurator() = MapScreenConfigurator(arguments)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onStart() {
        super.onStart()
        binding.mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        binding.mapView.onStop()
    }

    @SuppressLint("MissingPermission")
    override fun initViews() {
        with(binding) {
            val mapKit = MapKitFactory.getInstance()
            userLocationLayer = mapKit.createUserLocationLayer(mapView.mapWindow)
            mapKit.resetLocationManagerToDefault()
            userLocationLayer.isVisible = true
            userLocationLayer.isHeadingEnabled = false
            userLocationLayer.setObjectListener(this@MapFragmentView)
            moveCameraToUserLocation(Point(47.2357, 39.7015), INITIAL_ZOOM_LEVEL)

            openNavRailBtn.setOnClickListener { binding.navigationRail.slideInFromLeftIf() }
            profileBtn.clicks().emit(ProfileClicked)
            infoBtn.clicks().emit(InfoClicked)
            settingsBtn.clicks().emit(SettingsClicked)
            gpsIconBtn.setOnClickListener { moveCameraToUserLocation() }
        }
    }

    override fun render(state: MapState) {
        mapObjectTapListeners.clear()
        polylineMapObject = null

        state.availableTransport.forEach { transport ->
            val point = Point(transport.position.latitude, transport.position.longitude)
            val placemark = binding.mapView.map.mapObjects.addPlacemark(
                point,
                ImageProvider.fromResource(
                    requireContext(),
                    if (transport.transportType == TransportType.ELECTRIC_SCOOTER) {
                        R.drawable.ic_scooter
                    } else {
                        R.drawable.ic_bicycle
                    }
                )
            )
            placemark.userData = transport
            val mapObjectTapListener = MapObjectTapListener { _, _ ->
                buildRouteToPlacemark(placemark)
                true
            }
            mapObjectTapListeners.add(mapObjectTapListener)
            placemark.addTapListener(mapObjectTapListener)
        }
    }

    override fun onObjectAdded(userLocationView: UserLocationView) {
        userLocationView.arrow.setIcon(
            ImageProvider.fromResource(
                requireContext(), R.drawable.ic_map_self_icon
            )
        )

        val pinIcon = userLocationView.pin.useCompositeIcon()
        pinIcon.setIcon(
            "icon",
            ImageProvider.fromResource(requireContext(), R.drawable.ic_map_self_icon),
            IconStyle().setAnchor(PointF(0.5f, 0.5f))
                .setRotationType(RotationType.NO_ROTATION)
                .setZIndex(0f)
                .setScale(1f)
        )

        userLocationView.accuracyCircle.fillColor = requireContext().getColor(R.color.secondary_30)
    }

    override fun onObjectRemoved(p0: UserLocationView) {
    }

    override fun onObjectUpdated(p0: UserLocationView, p1: ObjectEvent) {

    }

    private fun moveCameraToUserLocation(
        targetPoint: Point? = userLocationLayer.cameraPosition()?.target,
        zoomLevel: Float = DEFAULT_ZOOM_LEVEL
    ) {
        targetPoint?.let {
            binding.mapView.map.move(
                CameraPosition(it, zoomLevel, 0.0f, 0.0f),
                Animation(Animation.Type.SMOOTH, 0.5f),
                null
            )
        }
    }

    private fun buildRouteToPlacemark(placemark: PlacemarkMapObject) {
        polylineMapObject?.let {
            binding.mapView.map.mapObjects.remove(it)
            polylineMapObject = null
        }

        val startPoint = userLocationLayer.cameraPosition()?.target ?: return
        val endPoint = placemark.geometry

        val pedestrianRouter = TransportFactory.getInstance().createPedestrianRouter()

        val requestPoints = mutableListOf(
            RequestPoint(startPoint, RequestPointType.WAYPOINT, null),
            RequestPoint(endPoint, RequestPointType.WAYPOINT, null)
        )
        val options = TimeOptions()

        pedestrianRouter.requestRoutes(
            requestPoints,
            options,
            this
        )
    }

    override fun onMasstransitRoutes(routes: MutableList<Route>) {
        if (routes.isNotEmpty()) {
            val route = routes[0]

            polylineMapObject?.let {
                binding.mapView.map.mapObjects.remove(it)
                polylineMapObject = null
            }

            val geometry = route.geometry
            val polylineMapObject = binding.mapView.map.mapObjects.addPolyline(geometry)
            polylineMapObject.setStrokeColor(R.color.outline)
            polylineMapObject.strokeWidth = 4f

            this.polylineMapObject = polylineMapObject
        }
    }

    override fun onMasstransitRoutesError(p0: com.yandex.runtime.Error) {
        mc.show(R.string.map_router_error)
    }

    private companion object {
        private const val DEFAULT_ZOOM_LEVEL = 15.0f
        private const val INITIAL_ZOOM_LEVEL = 8.0f
    }
}
