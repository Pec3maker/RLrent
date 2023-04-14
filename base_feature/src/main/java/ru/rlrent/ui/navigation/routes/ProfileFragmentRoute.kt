package ru.rlrent.ui.navigation.routes

import android.os.Bundle
import androidx.core.os.bundleOf
import ru.surfstudio.android.navigation.route.Route
import ru.surfstudio.android.navigation.route.fragment.FragmentRoute
import ru.rlrent.domain.users.User

/**
 * Роут экрана команды проекта.
 * [ru.surfstudio.practice.f_profile.ProfileFragmentView]
 */
class ProfileFragmentRoute(
    val user: User,
    val isSelfProfile: Boolean = false
) : FragmentRoute() {

    constructor(args: Bundle) : this(
        args.getSerializable(Route.EXTRA_FIRST) as User,
        args.getSerializable(Route.EXTRA_SECOND) as Boolean
    )

    override fun prepareData(): Bundle {
        return bundleOf(
            Route.EXTRA_FIRST to user,
            Route.EXTRA_SECOND to isSelfProfile
        )
    }

    override fun getScreenClassPath(): String {
        return "ru.surfstudio.practice.f_profile.ProfileFragmentView"
    }
}
