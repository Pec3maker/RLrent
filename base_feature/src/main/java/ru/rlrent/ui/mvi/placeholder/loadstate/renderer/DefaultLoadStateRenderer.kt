package ru.rlrent.ui.mvi.placeholder.loadstate.renderer

import ru.rlrent.ui.mvi.placeholder.loadstate.presentation.*
import ru.rlrent.ui.mvi.placeholder.loadstate.state.*
import ru.surfstudio.android.core.mvp.loadstate.BaseLoadStateRenderer
import ru.surfstudio.android.core.mvp.loadstate.LoadStateInterface
import ru.surfstudio.android.custom.view.placeholder.PlaceHolderViewContainer

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
        putPresentation(
            ErrorLoadState::class.java,
            ErrorLoadStatePresentation(placeHolderView, retryClick)
        )
        putPresentation(
            NoInternetLoadState::class.java,
            NoInternetLoadStatePresentation(placeHolderView, retryClick)
        )
        putPresentation(MainLoadingState::class.java, MainLoadingStatePresentation(placeHolderView))
        putPresentation(
            TransparentLoadingState::class.java,
            TransparentLoadingStatePresentation(placeHolderView)
        )
    }
}