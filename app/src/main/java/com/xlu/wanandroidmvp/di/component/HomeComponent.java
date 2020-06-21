package com.xlu.wanandroidmvp.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;
import com.xlu.wanandroidmvp.di.module.HomeModule;
import com.xlu.wanandroidmvp.module.home.HomeContract;
import com.xlu.wanandroidmvp.module.home.HomeFragment;

import dagger.BindsInstance;
import dagger.Component;

/**
 * @Author xlu
 * @Date 2020/6/21 0:20
 * @Description TODO
 */
@FragmentScope
@Component(modules = HomeModule.class, dependencies = AppComponent.class)
public interface HomeComponent {

    void inject(HomeFragment fragment);

    @Component.Builder
    interface Builder {

        @BindsInstance
        HomeComponent.Builder view(HomeContract.View view);

        HomeComponent.Builder appComponent(AppComponent appComponent);

        HomeComponent build();
    }
}