package ru.rlrent.f_main

import ru.android.rlrent.f_main.R
import ru.android.rlrent.f_main.databinding.ActivityMainBinding
import ru.rlrent.f_main.di.MainScreenConfigurator
import ru.rlrent.ui.mvi.view.BaseMviActivityView
import ru.surfstudio.android.core.mvi.impls.event.hub.ScreenEventHub
import ru.surfstudio.android.core.ui.view_binding.viewBinding
import ru.surfstudio.android.navigation.provider.container.FragmentNavigationContainer
import javax.inject.Inject

/**
 * Вью главного экрана
 */
internal class MainActivityView : BaseMviActivityView<MainState, MainEvent>(),
    FragmentNavigationContainer {

    @Inject
    override lateinit var hub: ScreenEventHub<MainEvent>

    @Inject
    override lateinit var sh: MainScreenStateHolder

    private val binding by viewBinding(ActivityMainBinding::bind) { rootView }

    override fun getScreenName(): String = "MainActivityView"

    override fun getContentView(): Int = R.layout.activity_main

    override val containerId: Int
        get() = R.id.main_fragment_container

    override fun createConfigurator() = MainScreenConfigurator(intent)

    override fun initViews() {
    }

    override fun render(state: MainState) {
    }
}
