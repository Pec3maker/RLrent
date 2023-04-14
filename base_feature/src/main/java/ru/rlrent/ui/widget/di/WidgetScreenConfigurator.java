package ru.rlrent.ui.widget.di;

import ru.rlrent.practice.ui.activity.di.ActivityComponent;
import ru.rlrent.ui.activity.di.ActivityComponent;
import ru.surfstudio.android.core.ui.activity.CoreActivityInterface;
import ru.surfstudio.android.mvp.widget.configurator.BaseWidgetViewConfigurator;
import ru.surfstudio.android.mvp.widget.delegate.ViewContextUnwrapper;
import ru.surfstudio.practice.ui.activity.di.ActivityComponent;
import ru.surfstudio.practice.ui.widget.di.WidgetScreenModule;

/**
 * Базовый конфигуратор для WidgetView
 */

public abstract class WidgetScreenConfigurator
        extends BaseWidgetViewConfigurator<ActivityComponent, WidgetScreenModule> {

    @Override
    protected WidgetScreenModule getWidgetScreenModule() {
        return new WidgetScreenModule(getPersistentScope());
    }

    @Override
    protected ActivityComponent getParentComponent() {
        return (ActivityComponent) (
                ViewContextUnwrapper.unwrapContext(
                        getTargetWidgetView().getContext(),
                        CoreActivityInterface.class
                )
        )
                .getPersistentScope()
                .getConfigurator()
                .getActivityComponent();
    }
}