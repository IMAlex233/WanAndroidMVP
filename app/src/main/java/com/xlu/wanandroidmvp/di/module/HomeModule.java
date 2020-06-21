package com.xlu.wanandroidmvp.di.module;

import androidx.fragment.app.Fragment;

import com.xlu.wanandroidmvp.http.api.WanAndroidService;
import com.xlu.wanandroidmvp.http.manger.NetWorkManager;
import com.xlu.wanandroidmvp.module.home.HomeContract;
import com.xlu.wanandroidmvp.module.home.HomeFragment;
import com.xlu.wanandroidmvp.module.home.HomeModel;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;


@Module
public abstract class HomeModule {

    @Binds
    abstract HomeContract.Model bindHomeModel(HomeModel model);

    @Provides
    static WanAndroidService provideService() {
        return NetWorkManager.getInstance().getWanAndroidService();
    }
}