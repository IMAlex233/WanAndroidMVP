package com.xlu.wanandroidmvp.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;
import com.xlu.wanandroidmvp.di.module.QAModule;
import com.xlu.wanandroidmvp.module.qa.QAContract;
import com.xlu.wanandroidmvp.module.qa.QAFragment;

import dagger.BindsInstance;
import dagger.Component;

@FragmentScope
@Component(modules = QAModule.class, dependencies = AppComponent.class)
public interface QAComponent {

    void inject(QAFragment fragment);

    @Component.Builder
    interface Builder {

        @BindsInstance
        QAComponent.Builder view(QAContract.View view);

        QAComponent.Builder appComponent(AppComponent appComponent);

        QAComponent build();
    }
}