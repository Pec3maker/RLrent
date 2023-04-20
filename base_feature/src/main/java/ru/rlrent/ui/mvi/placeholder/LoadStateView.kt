package ru.rlrent.ui.mvi.placeholder

import ru.rlrent.ui.mvi.placeholder.loadstate.state.*
import ru.surfstudio.android.core.mvp.binding.rx.request.data.Loading
import ru.surfstudio.android.core.mvp.loadstate.BaseLoadStateRenderer
import ru.surfstudio.android.core.mvp.loadstate.LoadStateInterface

/**
 * View interface, capable of displaying load-states of the [LoadStateType] set.
 */
interface LoadStateView {

    val renderer: BaseLoadStateRenderer?

    /**
     * Draw [loadStateType] on the screen.
     *
     * If null is passed, no state change and no rendering will be performed.
     */
    fun renderLoadState(loadStateType: LoadStateType?) {
        loadStateType?.getLoadState()?.let { renderer?.render(it) }
            ?: renderer?.render(NoneState())
    }

    fun renderLoadState(loading: Loading?) {
        if (loading is LoadStateType) {
            renderer?.render(loading.getLoadState())
        } else {
            renderer?.render(NoneState())
        }
    }

    fun getMainLoadState() = MainLoadingState()

    private fun LoadStateType.getLoadState(): LoadStateInterface {
        return when (this) {
            is LoadStateType.None -> NoneState()
            is LoadStateType.Main -> getMainLoadState()
            is LoadStateType.Transparent -> TransparentLoadingState()
            is LoadStateType.Error -> ErrorLoadState()
            is LoadStateType.NoInternet -> NoInternetLoadState()
        }
    }
}