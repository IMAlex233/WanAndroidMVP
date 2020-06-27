package com.xlu.wanandroidmvp.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;
import com.xlu.wanandroidmvp.common.AppConfig;
import com.xlu.wanandroidmvp.di.module.MineModule;
import com.xlu.wanandroidmvp.module.mine.MineContract;
import com.xlu.wanandroidmvp.module.mine.MineFragment;
import dagger.BindsInstance;
import dagger.Component;

@FragmentScope
@Component(modules = MineModule.class, dependencies = AppComponent.class)
public interface MineComponent {

    void inject(MineFragment fragment);

    @Component.Builder
    interface Builder {

        @BindsInstance
        MineComponent.Builder view(MineContract.View view);

        MineComponent.Builder appComponent(AppComponent appComponent);

        MineComponent build();
    }

    AppConfig appConfig();

}