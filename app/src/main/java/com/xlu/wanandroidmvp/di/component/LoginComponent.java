package com.xlu.wanandroidmvp.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.di.scope.FragmentScope;
import com.xlu.wanandroidmvp.di.module.LoginModule;
import com.xlu.wanandroidmvp.module.login.LoginActivity;
import com.xlu.wanandroidmvp.module.login.LoginContract;

import dagger.BindsInstance;
import dagger.Component;


@ActivityScope
@Component(modules = LoginModule.class, dependencies = AppComponent.class)
public interface LoginComponent {

    //void inject(LoginFragment fragment);
    void inject(LoginActivity activity);

    @Component.Builder
    interface Builder {

        @BindsInstance
        LoginComponent.Builder view(LoginContract.View view);

        LoginComponent.Builder appComponent(AppComponent appComponent);

        LoginComponent build();
    }
}