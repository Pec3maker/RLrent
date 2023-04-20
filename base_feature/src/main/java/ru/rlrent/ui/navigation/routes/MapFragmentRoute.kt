package ru.rlrent.ui.navigation.routes

import ru.surfstudio.android.navigation.route.fragment.FragmentRoute

/**
 * Роут экрана карты
 */
class MapFragmentRoute : FragmentRoute() {

    override fun getScreenClassPath(): String {
        return "ru.rlrent.f_map.MapFragmentView"
    }
}
