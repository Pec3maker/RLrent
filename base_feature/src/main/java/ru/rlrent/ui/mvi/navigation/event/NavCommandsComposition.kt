package ru.rlrent.ui.mvi.navigation.event

import ru.rlrent.ui.mvi.composition.SingleEventComposition

/**
 * Композиция событий навигации на основе команд
 */
interface NavCommandsComposition : SingleEventComposition<NavCommandsEvent>
