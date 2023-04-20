package ru.rlrent.ui.navigation.routes

import android.os.Bundle
import androidx.core.os.bundleOf
import ru.surfstudio.android.navigation.route.Route
import ru.surfstudio.android.navigation.route.fragment.FragmentRoute
import ru.surfstudio.android.utilktx.ktx.text.EMPTY_STRING

/**
 * Роут экрана регистрации
 */
class RegistrationPasswordFragmentRoute(
    val login: String,
    val phone: String
) : FragmentRoute() {

    constructor(args: Bundle) : this(
        args.getString(Route.EXTRA_FIRST) ?: EMPTY_STRING,
        args.getString(Route.EXTRA_SECOND) ?: EMPTY_STRING
    )

    override fun prepareData(): Bundle {
        return bundleOf(
            Route.EXTRA_FIRST to login,
            Route.EXTRA_SECOND to phone
        )
    }

    override fun getScreenClassPath(): String {
        return "ru.rlrent.f_registration.confirm_password.RegistrationPasswordFragmentView"
    }
}
