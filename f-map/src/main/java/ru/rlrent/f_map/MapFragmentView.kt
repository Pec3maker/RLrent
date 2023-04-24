package ru.rlrent.f_map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yandex.mapkit.MapKitFactory
import ru.android.rlrent.f_map.BuildConfig
import ru.android.rlrent.f_map.R
import ru.android.rlrent.f_map.databinding.FragmentMapBinding
import ru.rlrent.f_map.di.MapScreenConfigurator
import ru.rlrent.ui.mvi.view.BaseMviFragmentView
import ru.rlrent.v_message_controller_top.IconMessageController
import ru.surfstudio.android.core.mvi.impls.event.hub.ScreenEventHub
import ru.surfstudio.android.core.ui.navigation.feature.route.feature.CrossFeatureFragment
import ru.surfstudio.android.core.ui.view_binding.viewBinding
import javax.inject.Inject

/**
 * Вью аутентификации
 */
internal class MapFragmentView :
    BaseMviFragmentView<MapState, MapEvent>(),
    CrossFeatureFragment {
    @Inject
    override lateinit var hub: ScreenEventHub<MapEvent>

    @Inject
    override lateinit var sh: MapScreenStateHolder

    @Inject
    lateinit var ch: MapCommandHolder

    @Inject
    lateinit var mc: IconMessageController

    private val binding: FragmentMapBinding by viewBinding(FragmentMapBinding::bind)

    override fun createConfigurator() = MapScreenConfigurator(arguments)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitFactory.setApiKey(BuildConfig.MAPS_API_KEY)
        MapKitFactory.initialize(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart();
        binding.mapView.onStart()
    }

    override fun onStop() {
        binding.mapView.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop()
    }

    override fun render(state: MapState) {
    }

    override fun initInsets() {
    }

    override fun initViews() {
    }
}
