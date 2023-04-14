package ru.rlrent.ui.recylcer.controller

import android.view.ViewGroup
import ru.surfstudio.android.easyadapter.holder.BindableViewHolder
import ru.surfstudio.android.recycler.extension.sticky.controller.StickyHeaderBindableItemController
import ru.surfstudio.android.recycler.extension.sticky.item.StickyHeaderBindableItem
import ru.surfstudio.android.template.base_feature.R
import ru.surfstudio.android.template.base_feature.databinding.HeaderItemBinding

class HeaderController : StickyHeaderBindableItemController<String, HeaderController.Holder>() {

    override fun getItemId(item: StickyHeaderBindableItem<String, Holder>): String {
        return item.data.hashCode().toString()
    }

    override fun createViewHolder(parent: ViewGroup): Holder {
        return Holder(parent)
    }

    inner class Holder(parent: ViewGroup) :
        BindableViewHolder<String>(parent, R.layout.header_item) {

        private val binding = HeaderItemBinding.bind(itemView)

        override fun bind(header: String) {
            binding.headerTv.text = header
        }
    }
}
