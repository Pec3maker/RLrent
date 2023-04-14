package ru.rlrent.ui.recylcer.decorator

import android.content.Context
import ru.surfstudio.android.recycler.decorator.Decorator
import ru.surfstudio.android.recycler.decorator.MasterDecorator
import ru.surfstudio.android.template.base_feature.R
import ru.surfstudio.practice.base.utils.toPx

/**
 * Создание дефолтного дивайдера-разделителя, рисующегося поверх элемента
 *
 * @param context контекст, необходимый для инициализации
 * @param paddingStart отступ от начала контейнера
 * @param paddingEnd отступ от конца контейнер
 */
fun createDefaultLinearOverDividerDrawerDecorator(
    context: Context,
    paddingStart: Int = 16.toPx,
    paddingEnd: Int = 16.toPx,
    paddingTop: Int = 0,
    rules: Int = Rules.MIDDLE
): MasterDecorator = Decorator.Builder()
    .overlay(
        LinearDividerDrawer(
            Gap(
                color = context.getColor(R.color.lines),
                height = 1.toPx,
                paddingStart = paddingStart,
                paddingEnd = paddingEnd,
                paddingTop = paddingTop,
                rule = rules
            )
        )
    )
    .build()

/**
 * Создание дефолтного [LinearDividerDrawer]
 *
 * @param context контекст, необходимый для инициализации
 * @param paddingStart отступ от начала контейнера
 * @param paddingEnd отступ от конца контейнер
 * @param paddingTop отступ сверху от контейнера
 * @param rules правила отрисовки дивайдера
 */
fun createDefaultLinearDividerDrawer(
    context: Context,
    paddingStart: Int = 16.toPx,
    paddingEnd: Int = 16.toPx,
    paddingTop: Int = 0,
    rules: Int = Rules.MIDDLE
): LinearDividerDrawer {
    return LinearDividerDrawer(
        Gap(
            color = context.getColor(R.color.lines),
            height = 1.toPx,
            paddingStart = paddingStart,
            paddingEnd = paddingEnd,
            paddingTop = paddingTop,
            rule = rules
        )
    )
}
