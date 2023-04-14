package ru.rlrent.ui.placeholder.loadstate.presentation

import android.view.LayoutInflater
import ru.surfstudio.android.core.mvp.loadstate.SimpleLoadStatePresentation
import ru.surfstudio.android.custom.view.placeholder.PlaceHolderViewContainer
import ru.surfstudio.android.custom.view.placeholder.setClickableAndFocusable
import ru.surfstudio.android.template.base_feature.databinding.LayoutStateNoInternetBinding
import ru.surfstudio.practice.ui.placeholder.loadstate.state.NoInternetLoadState

class NoInternetLoadStatePresentation(
    private val placeHolder: PlaceHolderViewContainer,
    retryClickAction: () -> Unit
) : SimpleLoadStatePresentation<NoInternetLoadState>() {

    private val binding = LayoutStateNoInternetBinding.inflate(LayoutInflater.from(placeHolder.context))
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
