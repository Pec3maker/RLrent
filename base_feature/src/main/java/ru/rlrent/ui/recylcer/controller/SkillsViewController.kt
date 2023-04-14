package ru.rlrent.ui.recylcer.controller

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.ViewGroup
import com.google.android.material.chip.Chip
import ru.surfstudio.android.easyadapter.controller.BindableItemController
import ru.surfstudio.android.easyadapter.holder.BindableViewHolder
import ru.surfstudio.android.template.base_feature.R
import ru.surfstudio.android.template.base_feature.databinding.UserSkillsItemBinding
import ru.surfstudio.android.utilktx.ktx.text.EMPTY_STRING
import ru.rlrent.domain.users.Skills

class SkillsViewController :
    BindableItemController<Skills, SkillsViewController.Holder>() {

    override fun getItemId(skills: Skills): Any = skills.javaClass.name

    override fun createViewHolder(parent: ViewGroup) = Holder(parent)

    inner class Holder(parent: ViewGroup) :
        BindableViewHolder<Skills>(parent, R.layout.user_skills_item) {

        private val binding = UserSkillsItemBinding.bind(itemView)
        private val context = itemView.context

        override fun bind(skills: Skills) {
            with(binding) {
                chipGroup.removeAllViews()

                val mainSkill = skills.mainSkill
                val mainSkillChip = createChip(
                    context,
                    mainSkill.name,
                    mainSkill.backgroundColor,
                    mainSkill.textColor
                )
                chipGroup.addView(mainSkillChip)

                skills.otherSkills.forEach {
                    val skillChip = createChip(context, it)
                    chipGroup.addView(skillChip)
                }
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
