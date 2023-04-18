package ru.rlrent.ui.mvi.logger

import ru.android.rlrent.base_feature.BuildConfig.DEBUG
import ru.surfstudio.android.core.mvi.impls.event.hub.logging.EventLogger

/**
 * Логгер, который записывает эвенты только в дебажной сборке.
 */
class DebugEventLogger : EventLogger() {

    override val shouldLog: Boolean = DEBUG
}
