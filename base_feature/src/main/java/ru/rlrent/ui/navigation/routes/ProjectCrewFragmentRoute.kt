package ru.rlrent.ui.navigation.routes

import android.os.Bundle
import androidx.core.os.bundleOf
import ru.surfstudio.android.navigation.route.Route
import ru.surfstudio.android.navigation.route.fragment.FragmentRoute
import ru.surfstudio.android.utilktx.ktx.text.EMPTY_STRING

/**
 * Роут экрана команды проекта.
 * [ru.surfstudio.practice.f_project_crew.ProjectCrewFragmentView]
 */
class ProjectCrewFragmentRoute(
    val projectId: Int,
    val projectTitle: String
) : FragmentRoute() {

    constructor(args: Bundle) : this(
        args.getInt(Route.EXTRA_FIRST),
        args.getString(Route.EXTRA_SECOND) ?: EMPTY_STRING
    )

    override fun prepareData(): Bundle {
        return bundleOf(
            Route.EXTRA_FIRST to projectId,
            Route.EXTRA_SECOND to projectTitle
        )
    }

    override fun getScreenClassPath(): String {
        return "ru.surfstudio.practice.f_project_crew.ProjectCrewFragmentView"
    }
}
