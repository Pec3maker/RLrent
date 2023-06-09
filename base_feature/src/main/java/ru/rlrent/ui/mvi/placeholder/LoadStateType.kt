package ru.rlrent.ui.mvi.placeholder

import ru.surfstudio.android.core.mvp.binding.rx.request.data.Loading

sealed class LoadStateType : Loading {

    abstract val isSwrLoading: Boolean

    data class None(override val isSwrLoading: Boolean = false) : LoadStateType() {
        override val isLoading = isSwrLoading
    }

    object Main : LoadStateType() {
        override val isSwrLoading: Boolean = false
        override val isLoading: Boolean = true
    }

    object Transparent : LoadStateType() {
        override val isSwrLoading: Boolean = false
        override val isLoading: Boolean = true
    }

    object Error : LoadStateType() {
        override val isSwrLoading: Boolean = false
        override val isLoading: Boolean = false
    }

    object NoInternet : LoadStateType() {
        override val isSwrLoading: Boolean = false
        override val isLoading: Boolean = false
    }
}
