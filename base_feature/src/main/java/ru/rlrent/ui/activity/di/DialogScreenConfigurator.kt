package ru.rlrent.ui.activity.di

import android.os.Bundle
import ru.rlrent.ui.screen_modules.FragmentScreenModule
import ru.surfstudio.android.core.mvp.configurator.BaseFragmentViewConfigurator
import ru.surfstudio.android.core.ui.activity.CoreActivityInterface
import ru.surfstudio.android.mvp.dialog.complex.CoreDialogFragmentView

abstract class DialogScreenConfigurator(
    args: Bundle
) : BaseFragmentViewConfigurator<ActivityComponent, FragmentScreenModule>(args) {

    override fun getFragmentScreenModule(): FragmentScreenModule {
        return FragmentScreenModule(persistentScope)
    }

    override fun getParentComponent(): ActivityComponent {
        return (getTargetFragmentView<CoreDialogFragmentView>().activity as CoreActivityInterface)
            .persistentScope
            .configurator
            .activityComponent as ActivityComponent
    }
}