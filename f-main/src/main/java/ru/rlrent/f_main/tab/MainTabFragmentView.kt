package ru.rlrent.f_main.tab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.WindowInsetsCompat.Type.systemBars
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import ru.surfstudio.android.core.mvi.impls.event.hub.ScreenEventHub
import ru.surfstudio.android.core.mvp.loadstate.BaseLoadStateRenderer
import ru.surfstudio.android.core.ui.navigation.feature.route.feature.CrossFeatureFragment
import ru.surfstudio.android.core.ui.view_binding.viewBinding
import ru.surfstudio.android.navigation.provider.container.TabFragmentNavigationContainer
import ru.surfstudio.android.template.f_main.R
import ru.surfstudio.android.template.f_main.databinding.FragmentMainBinding
import ru.rlrent.f_main.tab.MainTabEvent.Input.TabSelected
import ru.rlrent.f_main.tab.MainTabEvent.Input.UserInfoClicked
import ru.rlrent.f_main.tab.adapter.MainViewPagerAdapter
import ru.rlrent.f_main.tab.di.MainTabScreenConfigurator
import ru.surfstudio.practice.ui.insets.InsetsApi
import ru.surfstudio.practice.ui.insets.InsetsApiBuilder
import ru.surfstudio.practice.ui.mvi.view.BaseMviFragmentView
import ru.surfstudio.practice.ui.placeholder.LoadStateView
import ru.surfstudio.practice.ui.placeholder.loadstate.renderer.DefaultLoadStateRenderer
import ru.surfstudio.practice.ui.util.distinctText
import ru.surfstudio.practice.ui.util.performIfChanged
import javax.inject.Inject

/**
 * Вью главного экрана
 */
internal class MainTabFragmentView :
    BaseMviFragmentView<MainTabState, MainTabEvent>(),
    TabFragmentNavigationContainer,
    CrossFeatureFragment,
    LoadStateView {

    @Inject
    override lateinit var hub: ScreenEventHub<MainTabEvent>

    @Inject
    override lateinit var sh: MainTabStateHolder

    private val binding: FragmentMainBinding by viewBinding(FragmentMainBinding::bind)

    override val renderer: BaseLoadStateRenderer by lazy {
        DefaultLoadStateRenderer(binding.placeholder, retryClick = {})
    }

    override fun createConfigurator() = MainTabScreenConfigurator(arguments)

    override val containerId: Int = R.id.fragment_container

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun getScreenName(): String = "MainBarFragmentView"

    override fun initInsets(): InsetsApi = InsetsApiBuilder()
        .addInsetMargin(binding.fragmentContainer, systemBars())
        .build()

    override fun initViews() {
        initListeners()
        initViewPager()
    }

    override fun render(state: MainTabState) {
        with(binding) {
            usernameTv.distinctText = state.currentUser.firstName
            loadImage(state.currentUser.avatar, avatarIv)

            placeholder.performIfChanged(state.phvState) { placeholderState ->
                renderLoadState(placeholderState)
            }
        }
    }

    private fun initListeners() = with(binding) {
        pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                TabSelected(position).emit()
            }
        })

        userInfo.setOnClickListener { UserInfoClicked.emit() }
    }

    private fun initViewPager() = with(binding) {
        val viewPagerAdapter = MainViewPagerAdapter(this@MainTabFragmentView)
        pager.adapter = viewPagerAdapter
        pager.offscreenPageLimit = 1
        TabLayoutMediator(tabs, pager) { tab, position ->
            tab.text = viewPagerAdapter.getPageTitle(position)
        }.attach()
    }

    private fun loadImage(imageUrl: String, iv: ImageView) {
        Glide.with(this)
            .load(imageUrl)
            .circleCrop()
            .into(iv)
    }
}
