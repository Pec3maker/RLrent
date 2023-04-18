package ru.rlrent.f_splash

import ru.surfstudio.android.navigation.route.activity.ActivityRoute

/**
 * Маршрут сплеша
 */
class SplashRoute : ActivityRoute() {

    override fun getScreenClassPath(): String {
        return "ru.android.rlrent.f_splash.SplashActivityView"
    }
}