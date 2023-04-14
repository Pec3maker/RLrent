package ru.rlrent.i_network.cache

import ru.rlrent.i_network.generated.urls.UrlProjects
import ru.rlrent.i_network.generated.urls.UrlUsers
import ru.rlrent.i_network.network.HttpMethods
import ru.rlrent.i_network.network.cache.SimpleCacheInfo
import javax.inject.Inject

/**
 * Хранилище информации о запросах, требующих кэширования
 */
class SimpleCacheInfoStorage @Inject constructor() {

    /**
     * Заполняется [SimpleCacheInfo].
     */
    val simpleCaches: Collection<SimpleCacheInfo> = listOf(
        SimpleCacheInfo(HttpMethods.GET, UrlProjects.GET_PROJECTS, "all_projects", 100),
        SimpleCacheInfo(HttpMethods.GET, UrlUsers.GET_USERS, "all_users", 100),
        SimpleCacheInfo(HttpMethods.GET, UrlUsers.GET_PROJECT_USERS, "all_project_users", 100)
    )
}
