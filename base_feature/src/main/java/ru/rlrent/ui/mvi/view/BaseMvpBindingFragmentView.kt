package ru.rlrent.ui.mvi.view

import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import ru.surfstudio.android.core.mvp.binding.rx.ui.BaseRxFragmentView

abstract class BaseMvpBindingFragmentView : BaseRxFragmentView() {

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?, viewRecreated: Boolean) {
        initViews()
        bind()
    }

    abstract fun initViews()

    abstract fun bind()

}
