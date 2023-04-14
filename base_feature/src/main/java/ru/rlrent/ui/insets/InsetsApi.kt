package ru.rlrent.ui.insets

import android.view.View
import androidx.core.graphics.Insets
import androidx.core.view.*
import androidx.core.view.WindowInsetsCompat.Type.ime
import ru.surfstudio.practice.ui.util.updateMargin
import kotlin.math.max

class InsetsApiBuilder {

    private val marginInsetsTypes: MutableMap<View, MutableSet<Int>> = mutableMapOf()
    private val paddingInsetsTypes: MutableMap<View, MutableSet<Int>> = mutableMapOf()

    fun build(): InsetsApi {
        return InsetsApi(
            marginInsetsTypes = marginInsetsTypes,
            paddingInsetsTypes = paddingInsetsTypes
        )
    }

    fun addInsetPadding(view: View, insetType: Int): InsetsApiBuilder {
        if (!paddingInsetsTypes.contains(view)) {
            paddingInsetsTypes[view] = mutableSetOf(insetType)
        } else {
            paddingInsetsTypes[view]?.add(insetType)
        }
        return this
    }

    fun addInsetMargin(view: View, insetType: Int): InsetsApiBuilder {
        if (!marginInsetsTypes.contains(view)) {
            marginInsetsTypes[view] = mutableSetOf(insetType)
        } else {
            marginInsetsTypes[view]?.add(insetType)
        }
        return this
    }

    companion object {
        val NO_INSETS = InsetsApiBuilder().build()
    }
}

class InsetsApi(
    private val marginInsetsTypes: Map<View, MutableSet<Int>> = mapOf(),
    private val paddingInsetsTypes: Map<View, MutableSet<Int>> = mapOf()
) {

    fun applyInsets() {
        val views = marginInsetsTypes.keys + paddingInsetsTypes.keys

        views.forEach { view ->
            var keyboardMargin = 0
            var keyboardPadding = 0

            val oldInsetsMargin = Insets.of(
                view.marginLeft,
                view.marginTop,
                view.marginRight,
                view.marginBottom
            )
            val oldInsetsPadding: Insets = Insets.of(
                view.paddingLeft,
                view.paddingTop,
                view.paddingRight,
                view.paddingBottom
            )

            ViewCompat.setOnApplyWindowInsetsListener(view) { _, insets ->

                var insetsMargin = Insets.NONE
                var insetsPadding = Insets.NONE

                // обработка всех инсетов, не учитывая клавиатуры
                paddingInsetsTypes[view]?.forEach {
                    if (it != ime()) {
                        insetsPadding = Insets.add(insetsPadding, insets.getInsets(it))
                    }
                }
                marginInsetsTypes[view]?.forEach {
                    if (it != ime()) {
                        insetsMargin = Insets.add(insetsMargin, insets.getInsets(it))
                    }
                }

                // обработка инсета клавиатуры
                when {
                    marginInsetsTypes[view]?.contains(ime()) == true -> {
                        keyboardMargin = insets.getInsets(ime()).bottom
                    }
                    paddingInsetsTypes[view]?.contains(ime()) == true -> {
                        keyboardPadding = insets.getInsets(ime()).bottom
                    }
                }

                // Учитываем старые отступы
                insetsPadding = Insets.add(insetsPadding, oldInsetsPadding)
                insetsMargin = Insets.add(insetsMargin, oldInsetsMargin)

                view.updatePadding(
                    left = insetsPadding.left,
                    top = insetsPadding.top,
                    right = insetsPadding.right,
                    bottom = max(keyboardPadding, insetsPadding.bottom)
                )

                view.updateMargin(
                    left = insetsMargin.left,
                    top = insetsMargin.top,
                    right = insetsMargin.right,
                    bottom = max(keyboardMargin, insetsMargin.bottom)
                )

                insets
            }
        }
    }
}
