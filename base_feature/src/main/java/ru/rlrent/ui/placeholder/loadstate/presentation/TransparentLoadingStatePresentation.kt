package ru.rlrent.ui.placeholder.loadstate.presentation

import android.view.LayoutInflater
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import ru.surfstudio.android.custom.view.placeholder.PlaceHolderViewContainer
import ru.surfstudio.android.core.mvp.loadstate.SimpleLoadStatePresentation
import ru.surfstudio.android.custom.view.placeholder.setClickableAndFocusable
import ru.surfstudio.android.template.base_feature.R
import ru.surfstudio.practice.ui.placeholder.loadstate.state.TransparentLoadingState
import ru.surfstudio.android.template.base_feature.databinding.LayoutLoadStateBinding

/**
 * Представление состояния TransparentLoading в виде ProgressBar поверх затемненного фона
 */
class TransparentLoadingStatePresentation(
    private val placeHolder: PlaceHolderViewContainer
) : SimpleLoadStatePresentation<TransparentLoadingState>() {

    private val binding = LayoutLoadStateBinding.inflate(LayoutInflater.from(placeHolder.context))

    @ColorInt
    var transparentBackgroundColor = ContextCompat.getColor(placeHolder.context, R.color.lucky_point_45)

    override fun showState(state: TransparentLoadingState) {
        with(placeHolder) {
            setBackgroundColor(transparentBackgroundColor)
            changeViewTo(binding.root)
            setClickableAndFocusable(true)
            show()
        }
    }
}
