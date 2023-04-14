package ru.rlrent.ui.recylcer.controller

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import ru.android.rlrent.base_feature.R
import ru.android.rlrent.base_feature.databinding.EmployeeCardItemBinding
import ru.rlrent.domain.users.User
import ru.rlrent.ui.util.getString
import ru.surfstudio.android.easyadapter.controller.BindableItemController
import ru.surfstudio.android.easyadapter.holder.BindableViewHolder
import ru.surfstudio.android.utilktx.ktx.text.EMPTY_STRING

class EmployeeViewController(
    private val onEmployeeClick: (User) -> Unit
) : BindableItemController<User, EmployeeViewController.Holder>() {

    override fun getItemId(employee: User) = employee.id

    override fun createViewHolder(parent: ViewGroup) = Holder(parent)

    inner class Holder(parent: ViewGroup) :
        BindableViewHolder<User>(parent, R.layout.employee_card_item) {

        private var data: User = User()
        private val binding = EmployeeCardItemBinding.bind(itemView)

        init {
            itemView.setOnClickListener { onEmployeeClick(data) }
        }

        override fun bind(employee: User) {
            data = employee

            val context = itemView.context

            with(binding) {

                employeeNameTv.text = itemView.getString(
                    R.string.employee_full_name,
                    employee.firstName,
                    employee.lastName
                )
                employeeRoleTv.text = employee.position

                chipGroup.removeAllViews()

                // Add main skill
                val mainSkill = employee.skills.mainSkill
                val mainSkillChip = createChip(
                    context,
                    mainSkill.name,
                    mainSkill.backgroundColor,
                    mainSkill.textColor
                )
                chipGroup.addView(mainSkillChip)

                // Add Other Skills
                employee.skills.otherSkills.forEach {
                    val skillChip = createChip(context, it)
                    chipGroup.addView(skillChip)
                }

                Glide.with(root)
                    .load(employee.avatar)
                    .placeholder(R.drawable.ic_random_logo)
                    .circleCrop()
                    .into(employeeAvatarIv)
            }
        }
    }

    private fun createChip(
        context: Context,
        chipText: String,
        backgroundColorString: String = EMPTY_STRING,
        textColorString: String = EMPTY_STRING
    ): Chip {

        val backgroundColor = try {
            Color.parseColor(backgroundColorString)
        } catch (ex: Exception) {
            context.getColor(R.color.lavender)
        }

        val textColor = try {
            Color.parseColor(textColorString)
        } catch (ex: Exception) {
            context.getColor(R.color.lucky_point)
        }

        return Chip(context).apply {
            text = chipText
            chipBackgroundColor = ColorStateList.valueOf(backgroundColor)
            isEnabled = false
            setEnsureMinTouchTargetSize(false)
            setTextAppearance(R.style.Text_Tag)
            setTextColor(ColorStateList.valueOf(textColor))
        }
    }
}
