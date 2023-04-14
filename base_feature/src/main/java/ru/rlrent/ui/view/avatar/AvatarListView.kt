package ru.rlrent.ui.view.avatar

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import ru.surfstudio.android.template.base_feature.R

class AvatarListView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyleAttr, defStyleRes) {

    init {
        orientation = HORIZONTAL
    }

    fun setIcons(iconList: List<String>) {
        (0 until childCount).forEach {
            getChildAt(it).isVisible = false
        }
        val textToDraw = "+${iconList.size - childCount - 1}"

        // Last element should be CircleTextView
        val circleTextView = getChildAt(childCount - 1)
        if (circleTextView !is CircleTextView) return
        circleTextView.text = textToDraw
        circleTextView.isVisible = iconList.size > childCount - 1

        // Elements should be CircleAvatarImageView, except the last one
        iconList.take(childCount - 1).forEachIndexed { index, url ->
            val view = getChildAt(index)
            if (view !is CircleAvatarImageView) return
            view.isVisible = true
            loadImage(url, view)
        }
    }

    private fun loadImage(imageUrl: String, iv: ImageView) {
        Glide.with(context)
            .load(imageUrl)
            .placeholder(R.drawable.ic_random_logo)
            .into(iv)
    }
}
