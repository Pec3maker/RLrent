package ru.rlrent.ui.navigation.routes

import android.os.Bundle
import androidx.core.os.bundleOf
import ru.surfstudio.android.navigation.route.Route
import ru.surfstudio.android.navigation.route.fragment.FragmentRoute
import ru.rlrent.domain.projects.Project

/**
 * Роут экрана детальной информации проекта.
 * [ru.surfstudio.practice.f_project_details.ProjectDetailsFragmentView]
 */
class ProjectDetailsFragmentRoute(val project: Project) : FragmentRoute() {

    constructor(args: Bundle) : this(
        args.getSerializable(Route.EXTRA_FIRST) as Project
    )

    override fun prepareData(): Bundle {
        return bundleOf(
            Route.EXTRA_FIRST to project
        )
    }

    override fun getScreenClassPath(): String {
        return "ru.surfstudio.practice.f_project_details.ProjectDetailsFragmentView"
    }
}
