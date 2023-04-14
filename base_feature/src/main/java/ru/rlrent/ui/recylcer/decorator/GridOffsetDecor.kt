package ru.rlrent.ui.recylcer.decorator

import android.graphics.Rect
import android.view.View
import androidx.annotation.Px
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.surfstudio.android.recycler.decorator.Decorator

/**
 * @param sideOffset отступ для ячеек слева и справа
 * @param middleOffset отступ между ячейками
 * @param topOffset отступ сверху
 * @param bottomOffset отступ снизу
 */
class GridOffsetDecor(
    @Px val sideOffset: Int = 0,
    @Px val middleOffset: Int = 0,
    @Px val topOffset: Int = 0,
    @Px val bottomOffset: Int = 0
) : Decorator.OffsetDecor {

    private val halfMiddleOffset = middleOffset / 2

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        recyclerView: RecyclerView,
        state: RecyclerView.State
    ) {
        val gridLayoutManger = recyclerView.layoutManager as? GridLayoutManager ?: return
        val spanCount = gridLayoutManger.spanCount
        val spanIndex =
            (recyclerView.getChildViewHolder(view).itemView.layoutParams as GridLayoutManager.LayoutParams).spanIndex

        when (spanIndex.rem(spanCount)) {
            0 -> outRect.set(sideOffset, topOffset, halfMiddleOffset, bottomOffset)
            spanCount - 1 -> outRect.set(halfMiddleOffset, topOffset, sideOffset, bottomOffset)
            else -> outRect.set(halfMiddleOffset, topOffset, halfMiddleOffset, bottomOffset)
        }
    }
}
