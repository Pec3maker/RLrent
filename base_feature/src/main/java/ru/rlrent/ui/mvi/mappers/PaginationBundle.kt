package ru.rlrent.ui.mvi.mappers

import ru.surfstudio.android.datalistlimitoffset.domain.datalist.DataList
import ru.surfstudio.android.easyadapter.pagination.PaginationState

/**
 * UI representation of paginated `DataList`.
 * */
data class PaginationBundle<T>(
    val list: DataList<T>? = null,
    val state: PaginationState? = null
) {

    val hasData = !list.isNullOrEmpty()

    /**
     * Check that `list` and `state` exist and if so - execute `callback`,
     * do nothing otherwise.
     * */
    fun safeGet(callback: (DataList<T>, PaginationState) -> Unit) {
        callback(list ?: return, state ?: return)
    }

    /**
     * Transform type of `PaginationBundle` and it's data.
     * */
    fun <R> transform(action: (T) -> R): PaginationBundle<R> {
        return PaginationBundle(list?.transform(action), state)
    }
}
