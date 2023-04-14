package ru.rlrent.ui.recylcer.controller

import android.view.ViewGroup
import ru.surfstudio.android.easyadapter.controller.BindableItemController
import ru.surfstudio.android.easyadapter.holder.BindableViewHolder
import ru.surfstudio.android.template.base_feature.R
import ru.surfstudio.android.template.base_feature.databinding.TitleItemBinding

class TitleViewController :
    BindableItemController<String, TitleViewController.Holder>() {

    override fun getItemId(title: String) = title

    override fun createViewHolder(parent: ViewGroup) = Holder(parent)

    inner class Holder(parent: ViewGroup) :
        BindableViewHolder<String>(parent, R.layout.title_item) {

        private val binding = TitleItemBinding.bind(itemView)

        override fun bind(title: String) {
            binding.titleTv.text = title
        }
    }
}
