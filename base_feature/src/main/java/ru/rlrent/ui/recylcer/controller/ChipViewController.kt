package ru.rlrent.ui.recylcer.controller

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import ru.surfstudio.android.easyadapter.controller.BindableItemController
import ru.surfstudio.android.easyadapter.holder.BindableViewHolder
import ru.surfstudio.android.template.base_feature.R
import ru.surfstudio.android.template.base_feature.databinding.ChipItemBinding
import ru.surfstudio.android.utilktx.ktx.text.EMPTY_STRING

data class ChipData(
    val chipText: String,
    val backgroundColorString: String = EMPTY_STRING,
    val textColorString: String = EMPTY_STRING
)

class ChipViewController(
    val onChipClickListener: (View) -> Unit
) : BindableItemController<ChipData, ChipViewController.Holder>() {

    override fun getItemId(chipData: ChipData) = chipData.chipText

    override fun createViewHolder(parent: ViewGroup) = Holder(parent)

    inner class Holder(parent: ViewGroup) :
        BindableViewHolder<ChipData>(parent, R.layout.chip_item) {

        private val binding = ChipItemBinding.bind(itemView)
        private val context = itemView.context

        init {
            with(binding) {
                clickableSpaceView.setOnClickListener {
                    onChipClickListener(root)
                }
                root.setOnFocusChangeListener { _, isChecked ->
                    chip.isChecked = isChecked
                }
            }
        }

        override fun bind(chipData: ChipData) {

            val uncheckedTextColor = context.getColor(R.color.lucky_point)
            val uncheckedBackgroundColor = context.getColor(R.color.secondary_background)

            val checkedBackgroundColor = try {
                Color.parseColor(chipData.backgroundColorString)
            } catch (ex: Exception) {
                uncheckedBackgroundColor
            }

            val checkedTextColor = try {
                Color.parseColor(chipData.textColorString)
            } catch (ex: Exception) {
                uncheckedTextColor
            }

            val checkedState = intArrayOf(android.R.attr.state_checked)
            val uncheckedState = intArrayOf(-android.R.attr.state_checked)

            val textColors = ColorStateList(
                arrayOf(checkedState, uncheckedState),
                intArrayOf(checkedTextColor, uncheckedTextColor)
            )
            val backgroundColors = ColorStateList(
                arrayOf(checkedState, uncheckedState),
                intArrayOf(checkedBackgroundColor, uncheckedBackgroundColor)
            )

            with(binding.chip) {
                chipBackgroundColor = backgroundColors
                setTextColor(textColors)
                text = chipData.chipText
            }
        }
    }
}
