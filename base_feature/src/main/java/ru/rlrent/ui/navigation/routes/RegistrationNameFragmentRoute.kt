package ru.rlrent.ui.navigation.routes

import ru.surfstudio.android.navigation.route.fragment.FragmentRoute

/**
 * Роут экрана регистрации
 */
class RegistrationNameFragmentRoute : FragmentRoute() {

    override fun getScreenClassPath(): String {
        return "ru.rlrent.f_registration.name.RegistrationNameFragmentView"
    }
}
