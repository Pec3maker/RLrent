package ru.rlrent.ui.recylcer.controller

import android.view.ViewGroup
import ru.surfstudio.android.easyadapter.controller.BindableItemController
import ru.surfstudio.android.easyadapter.holder.BindableViewHolder
import ru.surfstudio.android.template.base_feature.R
import ru.surfstudio.android.template.base_feature.databinding.UserFeatureItemBinding

data class UserFeature(
    val userFeatureTitle: String,
    val userFeatureValue: String
)

class UserFeatureViewController :
    BindableItemController<UserFeature, UserFeatureViewController.Holder>() {

    override fun getItemId(userFeature: UserFeature) = userFeature.hashCode()

    override fun createViewHolder(parent: ViewGroup) = Holder(parent)

    inner class Holder(parent: ViewGroup) :
        BindableViewHolder<UserFeature>(parent, R.layout.user_feature_item) {

        private val binding = UserFeatureItemBinding.bind(itemView)

        override fun bind(userFeature: UserFeature) {
            with(binding) {
                featureNameTv.text = userFeature.userFeatureTitle
                featureValueTv.text = userFeature.userFeatureValue
            }
        }
    }
}
