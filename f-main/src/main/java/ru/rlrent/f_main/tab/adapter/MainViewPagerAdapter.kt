package ru.rlrent.f_main.tab.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.surfstudio.practice.ui.navigation.routes.ProjectsFragmentRoute
import ru.surfstudio.practice.ui.navigation.routes.MainTabType
import ru.surfstudio.practice.ui.navigation.routes.EmployeesFragmentRoute
import ru.surfstudio.practice.ui.navigation.routes.AboutAppFragmentRoute

internal class MainViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private val tabTitles = arrayOf(PROJECTS_TAB_TITLE, EMPLOYEES_TAB_TITLE, ABOUT_TAB_TITLE)

    override fun getItemCount(): Int = MainTabType.values().size

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ProjectsFragmentRoute().createFragment()
            1 -> EmployeesFragmentRoute().createFragment()
            2 -> AboutAppFragmentRoute().createFragment()
            else -> throw IllegalArgumentException("Not correct task tab")
        }
    }

    fun getPageTitle(position: Int): CharSequence {
        return tabTitles[position]
    }
}

private const val PROJECTS_TAB_TITLE = "Проекты Surf"
private const val EMPLOYEES_TAB_TITLE = "Сотрудники"
private const val ABOUT_TAB_TITLE = "О приложении"