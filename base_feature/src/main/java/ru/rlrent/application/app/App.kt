package ru.rlrent.application.app

import android.app.Application
import android.os.StrictMode
import com.akaita.java.rxjava2debug.RxJava2Debug
import io.reactivex.plugins.RxJavaPlugins
import ru.surfstudio.android.activity.holder.ActiveActivityHolder
import ru.surfstudio.android.logger.Logger
import ru.surfstudio.android.navigation.provider.callbacks.ActivityNavigationProviderCallbacks
import ru.rlrent.android.template.base_feature.BuildConfig
import ru.surfstudio.android.template.base_feature.R
import ru.surfstudio.android.utilktx.ktx.ui.activity.ActivityLifecycleListener
import ru.surfstudio.android.utilktx.util.SdkUtils
import ru.android.rlrent.application.app.di.AppInjector
import ru.android.rlrent.application.logger.strategies.remote.FirebaseCrashlyticsRemoteLoggingStrategy
import ru.android.rlrent.application.logger.strategies.remote.RemoteLoggerLoggingStrategy
import ru.android.rlrent.application.logger.strategies.remote.timber.TimberLoggingStrategy
import ru.android.rlrent.base.logger.RemoteLogger

class App : Application() {

    val activeActivityHolder = ActiveActivityHolder()

    override fun onCreate() {
        super.onCreate()

        initLog()
        initVmPolicy()

        RxJavaPlugins.setErrorHandler { Logger.e(it) }
        AppInjector.initInjector(this)
        DebugAppInjector.initInjector(this, activeActivityHolder)
        registerActiveActivityListener()

        initRxJava2Debug()
        registerNavigationProviderCallbacks()
        DebugAppInjector.debugInteractor.onCreateApp(R.drawable.ic_android)
    }

    private fun registerNavigationProviderCallbacks() {
        val provider = AppInjector.appComponent.activityNavigationProvider()
        val callbackProvider = provider as? ActivityNavigationProviderCallbacks ?: return
        registerActivityLifecycleCallbacks(callbackProvider)
    }

    private fun initLog() {
        Logger.addLoggingStrategy(TimberLoggingStrategy())
        Logger.addLoggingStrategy(RemoteLoggerLoggingStrategy())
        RemoteLogger.addRemoteLoggingStrategy(FirebaseCrashlyticsRemoteLoggingStrategy())
    }

    private fun initVmPolicy() {
        if (SdkUtils.isAtLeastS()) {
            val policy = StrictMode.VmPolicy.Builder()
                .detectUnsafeIntentLaunch()
                .build()

            StrictMode.setVmPolicy(policy)
        }
    }

    private fun initRxJava2Debug() {
        RxJava2Debug.enableRxJava2AssemblyTracking(arrayOf(packageName))
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

    private fun isNotDebug(): Boolean = !ru.rlrent.android.template.base_feature.BuildConfig.BUILD_TYPE.contains("debug")

}