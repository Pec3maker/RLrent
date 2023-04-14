package ru.rlrent.ui.recylcer.controller

import android.view.ViewGroup
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import ru.android.rlrent.base_feature.R
import ru.android.rlrent.base_feature.databinding.ProjectCardItemBinding
import ru.surfstudio.android.easyadapter.controller.BindableItemController
import ru.surfstudio.android.easyadapter.holder.BindableViewHolder
import ru.rlrent.domain.projects.Project

class ProjectViewController(
    private val onProjectClick: (Project) -> Unit
) : BindableItemController<Project, ProjectViewController.Holder>() {

    override fun getItemId(project: Project) = project.id

    override fun createViewHolder(parent: ViewGroup) = Holder(parent)

    inner class Holder(parent: ViewGroup) :
        BindableViewHolder<Project>(parent, R.layout.project_card_item) {

        private var data: Project = Project()
        private val binding = ProjectCardItemBinding.bind(itemView)

        init {
            itemView.setOnClickListener { onProjectClick(data) }
        }

        override fun bind(project: Project) {
            data = project

            with(binding) {
                val hasMembers = project.countMembers != 0

                avatars.setIcons(project.avatarUrls)
                labelNoAvatarsTv.isVisible = !hasMembers
                avatars.isVisible = hasMembers
                projectTitleTv.text = project.name

                Glide.with(root)
                    .load(project.iconUrl)
                    .placeholder(R.drawable.ic_random_logo)
                    .circleCrop()
                    .into(projectIv)
            }
        }
    }
}
