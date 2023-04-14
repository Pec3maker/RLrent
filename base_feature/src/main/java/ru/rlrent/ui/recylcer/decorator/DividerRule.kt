package ru.rlrent.ui.recylcer.decorator

import androidx.annotation.IntDef

/**
 * Аннотация для ограничения списка значений правилами рисования Divider: [Rules]
 */
@Retention(AnnotationRetention.SOURCE)
@IntDef(
    Rules.START,
    Rules.MIDDLE,
    Rules.END
)
annotation class DividerRule
