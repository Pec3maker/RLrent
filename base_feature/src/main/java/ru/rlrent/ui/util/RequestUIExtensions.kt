package ru.rlrent.ui.util

import ru.surfstudio.android.core.mvp.binding.rx.request.data.RequestUi
import ru.surfstudio.practice.ui.placeholder.LoadStateType

val RequestUi<*>.placeholderState: LoadStateType
    get() = (load as? LoadStateType) ?: LoadStateType.Error

val RequestUi<*>.isSwrLoading: Boolean
    get() = (load as? LoadStateType)?.isSwrLoading ?: false
