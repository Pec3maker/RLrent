package ru.rlrent.ui.view.behavior

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout
import ru.surfstudio.android.template.base_feature.R
import ru.surfstudio.practice.base.utils.toPx

class CollapsingTextBehavior(
    context: Context,
    attrs: AttributeSet?
) : CoordinatorLayout.Behavior<View>(context, attrs) {

    private var targetId = 0
    private var targetX: Float = 0f
    private var targetY: Float = 0f
    private var viewX: Float = 0f
    private var viewY: Float = 0f
    private var targetWidth: Int = 0
    private var viewWidth: Int = 0
    private var isSetup = false

    init {
        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.CollapsingBehavior)
            targetId = a.getResourceId(R.styleable.CollapsingBehavior_collapsedTarget, 0)
            a.recycle()
        }
    }

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean = dependency is AppBarLayout

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {

        val appBarLayout = dependency as AppBarLayout

        // sets up the values of target and view
        if (!isSetup) {
            setup(parent, child, appBarLayout)
        }

        val range = appBarLayout.totalScrollRange
        val factor = -appBarLayout.y / range

        // calculates the new position of the view
        val left = viewX + (factor * (targetX - viewX)).toInt()
        val top = viewY + (factor * (targetY - viewY)).toInt()
        val width = viewWidth + (factor * (targetWidth - viewWidth)).toInt()

        // changing fontSize
        if (child is LinearLayout) {
            val newTextSize = (MAX_TITLE_SIZE - (factor * (MAX_TITLE_SIZE - MIN_TITLE_SIZE)))

            val title = child.getChildAt(0)
            if (title !is TextView) return false

            title.textSize = newTextSize
        }

        val lp = child.layoutParams as CoordinatorLayout.LayoutParams
        lp.width = width
        child.layoutParams = lp
        child.x = left
        child.y = top - COLLAPSED_MARGIN_TOP_DP.toPx
        return true
    }

    private fun setup(parent: CoordinatorLayout, child: View, appBarLayout: AppBarLayout) {
        val target = parent.findViewById<View>(targetId)

        viewX = child.x
        viewY = (appBarLayout.height / 2).toFloat() + EXPANDED_MARGIN_BOTTOM_DP.toPx
        viewWidth = child.width
        targetWidth = target.width

        var view = target
        while (view != parent) {
            targetX += view.x
            targetY += view.y
            view = view.parent as View
        }

        isSetup = true
    }

    companion object {
        const val MIN_TITLE_SIZE = 18
        const val MAX_TITLE_SIZE = 24
        const val EXPANDED_MARGIN_BOTTOM_DP = 48
        const val COLLAPSED_MARGIN_TOP_DP = 10
    }
}
