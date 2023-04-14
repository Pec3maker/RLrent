package ru.rlrent.ui.placeholder.loadstate.renderer

import ru.surfstudio.android.core.mvp.loadstate.BaseLoadStateRenderer
import ru.surfstudio.android.core.mvp.loadstate.LoadStateInterface
import ru.surfstudio.android.custom.view.placeholder.PlaceHolderViewContainer
import ru.surfstudio.practice.ui.placeholder.loadstate.presentation.*
import ru.surfstudio.practice.ui.placeholder.loadstate.state.*

/**
 * Проектная реализация BaseLoadStateRenderer
 */
class DefaultLoadStateRenderer(
        placeHolderView: PlaceHolderViewContainer,
        override val defaultState: LoadStateInterface = NoneState(),
        retryClick: () -> Unit
) : BaseLoadStateRenderer() {
    init {
        putPresentation(NoneState::class.java, NoneLoadStatePresentation(placeHolderView))
        putPresentation(ErrorLoadState::class.java, ErrorLoadStatePresentation(placeHolderView, retryClick))
        putPresentation(NoInternetLoadState::class.java, NoInternetLoadStatePresentation(placeHolderView, retryClick))
        putPresentation(MainLoadingState::class.java, MainLoadingStatePresentation(placeHolderView))
        putPresentation(TransparentLoadingState::class.java, TransparentLoadingStatePresentation(placeHolderView))
    }
}