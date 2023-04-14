package ru.rlrent.ui.recylcer.controller

import android.view.ViewGroup
import androidx.annotation.ColorRes
import com.bumptech.glide.Glide
import ru.surfstudio.android.easyadapter.controller.BindableItemController
import ru.surfstudio.android.easyadapter.holder.BindableViewHolder
import ru.surfstudio.android.template.base_feature.R
import ru.surfstudio.android.template.base_feature.databinding.ProfileProjectCardItemBinding
import ru.surfstudio.android.utilktx.ktx.text.EMPTY_STRING
import ru.rlrent.domain.users.CurrentProject

// todo удалить когда сервер начнет выдавать цвета
enum class ProjectColors(@ColorRes val backgroundColor: Int, @ColorRes val primaryColor: Int) {
    BLUE(R.color.blue_10, R.color.blue),
    GREEN(R.color.green_10, R.color.green),
    RED(R.color.red_10, R.color.red),
    ORANGE(R.color.orange_10, R.color.orange)
}

class ProfileProjectViewController :
    BindableItemController<CurrentProject, ProfileProjectViewController.Holder>() {

    override fun getItemId(project: CurrentProject) = project.id

    override fun createViewHolder(parent: ViewGroup) = Holder(parent)

    inner class Holder(parent: ViewGroup) :
        BindableViewHolder<CurrentProject>(parent, R.layout.profile_project_card_item) {

        private val binding = ProfileProjectCardItemBinding.bind(itemView)

        override fun bind(project: CurrentProject) {
            with(binding) {
                // todo изменить когда сервер начнет выдавать цвета
                val color = ProjectColors.values().random()

                projectBackgroundFv.color = itemView.context.getColor(color.backgroundColor)
                projectLabelTv.setTextColor(itemView.context.getColor(color.primaryColor))
                projectNameTv.text =
                    itemView.context.getString(R.string.profile_project_name, project.name)
                percentTv.text = project.rate
                percentTv.setTextColor(itemView.context.getColor(color.primaryColor))

                Glide.with(root)
                    .load(EMPTY_STRING) // добавить url когда сервер начнет отдавать url
                    .placeholder(R.drawable.ic_random_logo)
                    .circleCrop()
                    .into(projectIconIv)
            }
        }
    }
}
