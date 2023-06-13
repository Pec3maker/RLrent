package ru.rlrent.ui.navigation.routes

import ru.surfstudio.android.navigation.route.fragment.FragmentRoute

class AuthFragmentRoute : FragmentRoute() {

    override fun getScreenClassPath(): String {
        return "ru.rlrent.f_auth.auth.AuthFragmentView"
    }
}
