package ru.rlrent.ui.mvi.navigation.base

import ru.rlrent.ui.mvi.navigation.event.NavCommandsEvent
import ru.surfstudio.android.core.mvi.ui.middleware.RxMiddleware

/**
 * Interface of [RxMiddleware] which can process navigation events.
 */
interface NavigationMiddleware : RxMiddleware<NavCommandsEvent>