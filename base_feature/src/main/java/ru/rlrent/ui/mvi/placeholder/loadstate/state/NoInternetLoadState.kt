package ru.rlrent.ui.mvi.placeholder.loadstate.state

import ru.surfstudio.android.core.mvp.loadstate.LoadStateInterface

class NoInternetLoadState : LoadStateInterface {
    override fun equals(other: Any?): Boolean {
        return other is NoInternetLoadState
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}