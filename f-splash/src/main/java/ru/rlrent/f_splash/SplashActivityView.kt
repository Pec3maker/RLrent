package ru.rlrent.f_splash

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import ru.android.rlrent.f_splash.R
import ru.rlrent.f_splash.di.SplashScreenConfigurator
import ru.rlrent.ui.mvi.view.BaseMviActivityView
import ru.surfstudio.android.core.mvi.impls.event.hub.ScreenEventHub
import ru.surfstudio.android.utilktx.util.SdkUtils
import javax.inject.Inject

@SuppressLint("CustomSplashScreen")
internal class SplashActivityView : BaseMviActivityView<SplashState, SplashEvent>() {

    @Inject
    override lateinit var hub: ScreenEventHub<SplashEvent>

    @Inject
    override lateinit var sh: SplashScreenStateHolder

    override fun getScreenName(): String = "SplashActivityView"

    override fun createConfigurator() = SplashScreenConfigurator(intent)

    override fun getContentView(): Int = R.layout.activity_splash

    override fun onCreate(
        savedInstanceState: Bundle?,
        persistentState: PersistableBundle?,
        viewRecreated: Boolean
    ) {
        if (SdkUtils.isAtLeastS()) {
            // требуется вызывать этот метод до вызова setContentView(..),
            // который вызывается в super.onCreate(..)
            installSplashScreen()
        }
        super.onCreate(savedInstanceState, persistentState, viewRecreated)

        if (SdkUtils.isAtLeastS()) {
            // не показываем этот экран, т.к. отображается системный сплеш
            val content: View = findViewById(android.R.id.content)
            content.viewTreeObserver.addOnPreDrawListener { false }
        }
    }

    override fun render(state: SplashState) {

    }

    override fun initViews() {
    }
}