package ru.rlrent.ui.mvi.composition

import ru.surfstudio.android.core.mvi.event.Event
import ru.surfstudio.android.core.mvi.event.composition.CompositionEvent

interface SingleEventComposition<C : Event> : CompositionEvent<C> {

    var event: C

    override var events: List<C>
        get() = listOf(event)
        set(value) {
            event = value.first()
        }
}
