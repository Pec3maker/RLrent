package ru.rlrent.application.app

import android.app.Application
import android.os.StrictMode
import com.yandex.mapkit.MapKitFactory
import io.reactivex.plugins.RxJavaPlugins
import ru.android.rlrent.base_feature.BuildConfig
import ru.rlrent.application.app.di.AppInjector
import ru.surfstudio.android.activity.holder.ActiveActivityHolder
import ru.surfstudio.android.logger.Logger
import ru.surfstudio.android.navigation.provider.callbacks.ActivityNavigationProviderCallbacks
import ru.surfstudio.android.utilktx.ktx.ui.activity.ActivityLifecycleListener
import ru.surfstudio.android.utilktx.util.SdkUtils

class App : Application() {

    val activeActivityHolder = ActiveActivityHolder()

    override fun onCreate() {
        super.onCreate()

        initVmPolicy()

        RxJavaPlugins.setErrorHandler { Logger.e(it) }
        AppInjector.initInjector(this)
        registerActiveActivityListener()

        registerNavigationProviderCallbacks()
        MapKitFactory.setApiKey(BuildConfig.MAPS_API_KEY)
    }

    private fun registerNavigationProviderCallbacks() {
        val provider = AppInjector.appComponent.activityNavigationProvider()
        val callbackProvider = provider as? ActivityNavigationProviderCallbacks ?: return
        registerActivityLifecycleCallbacks(callbackProvider)
    }

    private fun initVmPolicy() {
        if (SdkUtils.isAtLeastS()) {
            val policy = StrictMode.VmPolicy.Builder()
                .detectUnsafeIntentLaunch()
                .build()

            StrictMode.setVmPolicy(policy)
        }
    }

    /**
     * Регистрирует слушатель аткивной активити
     */
    private fun registerActiveActivityListener() {
        registerActivityLifecycleCallbacks(AppInjector.appComponent.navigationCallbacks())
        registerActivityLifecycleCallbacks(
            ActivityLifecycleListener(
                onActivityResumed = { activity ->
                    activeActivityHolder.activity = activity
                },
                onActivityPaused = {
                    activeActivityHolder.clearActivity()
                }
            )
        )
    }
}