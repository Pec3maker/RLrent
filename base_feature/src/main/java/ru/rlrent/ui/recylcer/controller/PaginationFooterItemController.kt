package ru.rlrent.ui.recylcer.controller

import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import ru.surfstudio.android.easyadapter.pagination.EasyPaginationAdapter
import ru.surfstudio.android.easyadapter.pagination.PaginationState
import ru.surfstudio.android.template.base_feature.R

class PaginationFooterItemController : EasyPaginationAdapter.BasePaginationFooterController<PaginationFooterItemController.Holder>() {

    override fun createViewHolder(
        parent: ViewGroup,
        listener: EasyPaginationAdapter.OnShowMoreListener
    ): Holder {
        return Holder(parent, listener)
    }

    inner class Holder(
        parent: ViewGroup,
        listener: EasyPaginationAdapter.OnShowMoreListener
    ) : EasyPaginationAdapter.BasePaginationFooterHolder(
        parent,
        R.layout.layout_pagination_footer
    ) {

        private val loadingIndicator: ProgressBar =
            itemView.findViewById(R.id.pagination_footer_progress_bar)
        private val showMoreTv: TextView = itemView.findViewById(R.id.pagination_footer_tv)
        private val reloadBtn: Button = itemView.findViewById(R.id.reload_btn)

        init {
            reloadBtn.setOnClickListener { listener.onShowMore() }
        }

        override fun bind(state: PaginationState) {
            loadingIndicator.isVisible = state == PaginationState.READY
            showMoreTv.isVisible = state == PaginationState.ERROR
            reloadBtn.isVisible = state == PaginationState.ERROR
        }
    }
}
