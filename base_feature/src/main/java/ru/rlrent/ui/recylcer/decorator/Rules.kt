package ru.rlrent.ui.recylcer.decorator

/**
 * Правила рисования дивайдеров
 *
 * Правила могут быть скомбинированы между собой
 *
 * @property START - дивайдер рисуется только в начале списка
 * @property MIDDLE - дивайдер рисуется в середине списка между элементами
 * @property END - дивайдер рисуется только в конце списка
 */
object Rules {
    const val START = 1
    const val MIDDLE = 2
    const val END = 4

    fun checkMiddleRule(rule: Int): Boolean {
        return rule and MIDDLE != 0
    }

    fun checkEndRule(rule: Int): Boolean {
        return rule and END != 0
    }

    fun checkStartRule(rule: Int): Boolean {
        return rule and START != 0
    }

    fun checkAllRule(rule: Int): Boolean {
        return rule and (START or MIDDLE or END) != 0
    }
}
