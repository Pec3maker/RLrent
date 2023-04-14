package ru.rlrent.ui.recylcer.decorator

import android.graphics.Rect
import android.view.View
import androidx.annotation.Px
import androidx.recyclerview.widget.RecyclerView
import ru.surfstudio.android.recycler.decorator.Decorator

/**
 * Декор для выставления отступов
 * @param sideOffset отступ в начале и конце ресайклера
 * @param topOffset отступ сверху
 * @param bottomOffset отступ снизу
 */
class SimpleOffsetDecor(
    @Px val sideOffset: Int = 0,
    @Px val topOffset: Int = 0,
    @Px val bottomOffset: Int = 0
) : Decorator.OffsetDecor {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        recyclerView: RecyclerView,
        state: RecyclerView.State
    ) = outRect.set(sideOffset, topOffset, sideOffset, bottomOffset)
}
