package ru.rlrent.ui.recylcer.decorator

import android.graphics.Rect
import android.view.View
import androidx.annotation.Px
import androidx.recyclerview.widget.RecyclerView
import ru.surfstudio.android.recycler.decorator.Decorator

/**
 * Декор для выставления отступов в списке отдельно для внутренних и крайних элементов
 * @param sideOffset отступ в начале и конце ресайклера
 * @param middleOffset отступ внутри между ячейками
 * @param topOffset отступ сверху
 * @param bottomOffset отступ снизу
 */
class ListItemOffsetDecor(
    @Px val sideOffset: Int = 0,
    @Px val middleOffset: Int = 0,
    @Px val topOffset: Int = 0,
    @Px val bottomOffset: Int = 0
) : Decorator.OffsetDecor {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        recyclerView: RecyclerView,
        state: RecyclerView.State
    ) {
        val viewHolder = recyclerView.getChildViewHolder(view)

        when (viewHolder.absoluteAdapterPosition) {
            0 -> {
                outRect.set(sideOffset, topOffset, sideOffset, 0)
            }
            (recyclerView.adapter?.itemCount ?: 0) - 1 -> {
                outRect.set(sideOffset, 0, sideOffset, bottomOffset)
            }
            else -> {
                outRect.set(sideOffset, 0, sideOffset, middleOffset)
            }
        }
    }
}
