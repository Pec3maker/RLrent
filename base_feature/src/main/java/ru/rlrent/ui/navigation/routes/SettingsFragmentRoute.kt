package ru.rlrent.ui.navigation.routes

import ru.surfstudio.android.navigation.route.fragment.FragmentRoute

/**
 * Роут экрана настроек
 */
class SettingsFragmentRoute : FragmentRoute() {

    override fun getScreenClassPath(): String {
        return "ru.rlrent.f_settings.SettingsFragmentView"
    }
}
