package ru.rlrent.ui.mvi.dialog

import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import ru.rlrent.ui.mvi.view.SingleStateView
import ru.surfstudio.android.core.mvi.event.Event
import ru.surfstudio.android.core.mvi.event.hub.owner.SingleHubOwner
import ru.surfstudio.android.core.mvp.binding.rx.ui.BaseRxDialogView
import ru.surfstudio.android.core.mvp.binding.rx.ui.BaseRxPresenter

/**
 * Базовый класс для диалога с сущностями MVi
 */
abstract class BaseMviDialogFragment<S, E : Event> : BaseRxDialogView(),
    SingleHubOwner<E>,
    SingleStateView<S> {

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?, viewRecreated: Boolean) {
        initViews()
        bind()
    }

    override fun getPresenters(): Array<BaseRxPresenter> {
        return emptyArray()
    }

    @CallSuper
    protected open fun bind() {
        sh bindTo ::render
    }
}