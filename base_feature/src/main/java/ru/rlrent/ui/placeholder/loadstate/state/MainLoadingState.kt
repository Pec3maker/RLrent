package ru.rlrent.ui.placeholder.loadstate.state

import androidx.annotation.LayoutRes
import ru.surfstudio.android.core.mvp.loadstate.LoadStateInterface

class MainLoadingState(@LayoutRes val idShimmerLayout: Int? = null) : LoadStateInterface {
    override fun equals(other: Any?): Boolean {
        return other is MainLoadingState
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}