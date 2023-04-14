package ru.rlrent.ui.recylcer.controller

import android.view.View
import android.view.ViewGroup
import ru.surfstudio.android.easyadapter.controller.NoDataItemController
import ru.surfstudio.android.easyadapter.holder.BaseViewHolder
import ru.surfstudio.android.template.base_feature.R
import ru.surfstudio.android.template.base_feature.databinding.ButtonItemBinding

class BtnViewController(
    val textBtn: String,
    val onClickListener: (View) -> Unit
) : NoDataItemController<BtnViewController.Holder>() {

    override fun createViewHolder(parent: ViewGroup) = Holder(parent)

    inner class Holder(parent: ViewGroup) :
        BaseViewHolder(parent, R.layout.button_item) {
        private val binding = ButtonItemBinding.bind(itemView)

        init {
            with(binding.connectBtn) {
                setOnClickListener(onClickListener)
                text = textBtn
            }
        }
    }
}
