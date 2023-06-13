package ru.rlrent.ui.util

import ru.rlrent.ui.mvi.placeholder.LoadStateType
import ru.surfstudio.android.core.mvp.binding.rx.request.data.RequestUi

val RequestUi<*>.placeholderState: LoadStateType
    get() = (load as? LoadStateType) ?: LoadStateType.Error

val RequestUi<*>.isSwrLoading: Boolean
    get() = (load as? LoadStateType)?.isSwrLoading ?: false
