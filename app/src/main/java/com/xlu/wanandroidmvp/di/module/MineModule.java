package com.xlu.wanandroidmvp.di.module;

import com.xlu.wanandroidmvp.common.AppConfig;
import com.xlu.wanandroidmvp.http.api.WanAndroidService;
import com.xlu.wanandroidmvp.http.manger.NetWorkManager;
import com.xlu.wanandroidmvp.module.mine.MineContract;
import com.xlu.wanandroidmvp.module.mine.MineModel;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class MineModule {

    @Binds
    abstract MineContract.Model bindContainerModel(MineModel model);

    @Provides
    static AppConfig provideAppConfig() {
        return AppConfig.getInstance();
    }

    @Provides
    static WanAndroidService provideWanAndroidService() {
        return NetWorkManager.getInstance().getWanAndroidService();
    }
}