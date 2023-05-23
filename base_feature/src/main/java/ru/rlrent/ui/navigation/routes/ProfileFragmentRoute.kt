package ru.rlrent.ui.navigation.routes

import android.os.Bundle
import androidx.core.os.bundleOf
import ru.rlrent.domain.user.User
import ru.surfstudio.android.navigation.route.Route
import ru.surfstudio.android.navigation.route.fragment.FragmentRoute

/**
 * Роут экрана профиля
 */
class ProfileFragmentRoute(
    val user: User
) : FragmentRoute() {

    constructor(args: Bundle) : this(
        args.getSerializable(Route.EXTRA_FIRST) as User
    )

    override fun prepareData(): Bundle {
        return bundleOf(
            Route.EXTRA_FIRST to user
        )
    }

    override fun getScreenClassPath(): String {
        return "ru.rlrent.f_profile.ProfileFragmentView"
    }
}
