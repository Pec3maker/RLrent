package ru.rlrent.ui.mvi.placeholder.loadstate.presentation

import android.view.LayoutInflater
import android.view.View
import ru.android.rlrent.base_feature.R
import ru.rlrent.ui.mvi.placeholder.loadstate.state.MainLoadingState
import ru.surfstudio.android.core.mvp.loadstate.SimpleLoadStatePresentation
import ru.surfstudio.android.custom.view.placeholder.PlaceHolderViewContainer
import ru.surfstudio.android.custom.view.placeholder.setClickableAndFocusable

class MainLoadingStatePresentation(
    private val placeHolder: PlaceHolderViewContainer
) : SimpleLoadStatePresentation<MainLoadingState>() {

    private val defaultView: View by lazy {
        LayoutInflater.from(placeHolder.context)
            .inflate(R.layout.layout_load_state, placeHolder, false)
    }

    private var viewShimmer: View? = null

    override fun showState(state: MainLoadingState) {
        with(placeHolder) {
            changeViewTo(getViewLoading(state) ?: defaultView)
            setClickableAndFocusable(true)
            show()
        }
    }

    private fun getViewLoading(state: MainLoadingState): View? {
        viewShimmer?.let { return it }
        state.idShimmerLayout?.let {
            val newView = LayoutInflater.from(placeHolder.context)
                .inflate(it, placeHolder, false)
            viewShimmer = newView
            return newView
        }
        return null
    }
}
