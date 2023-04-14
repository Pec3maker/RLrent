package ru.rlrent.ui.navigation.routes

import ru.surfstudio.android.navigation.route.fragment.FragmentRoute

/**
 * Роут главного экрана с табами
 */
class MainTabRoute : FragmentRoute() {

    override fun getScreenClassPath(): String {
        return "ru.surfstudio.practice.f_main.tab.MainTabFragmentView"
    }
}
