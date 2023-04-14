package ru.rlrent.ui.placeholder.loadstate.presentation

import android.view.LayoutInflater
import androidx.annotation.StringRes
import androidx.viewbinding.ViewBinding
import ru.surfstudio.practice.ui.placeholder.loadstate.state.ErrorLoadState
import ru.surfstudio.android.core.mvp.loadstate.SimpleLoadStatePresentation
import ru.surfstudio.android.custom.view.placeholder.PlaceHolderViewContainer
import ru.surfstudio.android.custom.view.placeholder.setClickableAndFocusable
import ru.surfstudio.android.template.base_feature.R
import ru.surfstudio.android.template.base_feature.databinding.LayoutStateErrorBinding

/**
 * Представление состояния ErrorLoadState, с картинкой, тайтлом, сабтайтлом и кнопкой
 */
class ErrorLoadStatePresentation(
        private val placeHolder: PlaceHolderViewContainer,
        private val retryClick: () -> Unit,
) : SimpleLoadStatePresentation<ErrorLoadState>() {

    private val binding: LayoutStateErrorBinding = LayoutStateErrorBinding.inflate(LayoutInflater.from(placeHolder.context))

    init {
        binding.errorLoadStateBtn.setOnClickListener { retryClick() }
    }

    override fun showState(state: ErrorLoadState) {
        with(placeHolder) {
            changeViewTo(binding.root)
            setClickableAndFocusable(true)
            show()
        }
    }
}