package ru.rlrent.ui.mvi.placeholder.loadstate.presentation

import android.view.LayoutInflater
import ru.android.rlrent.base_feature.databinding.LayoutStateNoInternetBinding
import ru.rlrent.ui.mvi.placeholder.loadstate.state.NoInternetLoadState
import ru.surfstudio.android.core.mvp.loadstate.SimpleLoadStatePresentation
import ru.surfstudio.android.custom.view.placeholder.PlaceHolderViewContainer
import ru.surfstudio.android.custom.view.placeholder.setClickableAndFocusable

class NoInternetLoadStatePresentation(
    private val placeHolder: PlaceHolderViewContainer,
    retryClickAction: () -> Unit
) : SimpleLoadStatePresentation<NoInternetLoadState>() {

    private val binding =
        LayoutStateNoInternetBinding.inflate(LayoutInflater.from(placeHolder.context))
    private val view = binding.root

    init {
        binding.errorLoadStateBtn.setOnClickListener { retryClickAction() }
    }

    override fun showState(state: NoInternetLoadState) {
        with(placeHolder) {
            changeViewTo(view)
            setClickableAndFocusable(true)
            show()
        }
    }
}
