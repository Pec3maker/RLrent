package ru.rlrent.ui.mvi.placeholder.loadstate.presentation

import android.view.View
import ru.rlrent.ui.mvi.placeholder.loadstate.state.NoneState
import ru.surfstudio.android.core.mvp.loadstate.SimpleLoadStatePresentation
import ru.surfstudio.android.custom.view.placeholder.PlaceHolderViewContainer

/**
 * Представление состояния NoneState, скрывающее PlaceHolderViewContainer
 */
class NoneLoadStatePresentation(
    private val placeHolder: PlaceHolderViewContainer
) : SimpleLoadStatePresentation<NoneState>() {

    override fun showState(state: NoneState) {
        with(placeHolder) {
            changeViewTo(View(context))
            hide()
        }
    }
}