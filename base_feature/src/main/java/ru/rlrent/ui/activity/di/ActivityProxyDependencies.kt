package ru.rlrent.ui.activity.di

import ru.surfstudio.android.core.ui.navigation.fragment.FragmentNavigator
import ru.surfstudio.android.core.ui.navigation.fragment.tabfragment.TabFragmentNavigator
import ru.surfstudio.android.core.ui.provider.ActivityProvider
import ru.surfstudio.android.core.ui.scope.ActivityPersistentScope
import ru.surfstudio.android.dagger.scope.PerActivity
import ru.surfstudio.android.rxbus.RxBus

interface ActivityProxyDependencies {
    fun activityProvider(): ActivityProvider
    fun activityPersistentScope(): ActivityPersistentScope
    fun fragmentNavigator(): FragmentNavigator
    fun tabFragmentNavigator(): TabFragmentNavigator
    fun rxBus(): RxBus
}