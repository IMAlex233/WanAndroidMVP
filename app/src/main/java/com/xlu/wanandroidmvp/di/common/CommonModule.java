package com.xlu.wanandroidmvp.di.common;


import com.xlu.wanandroidmvp.http.bean.UserData;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

/**
 * @Author xlu
 * @Date 2020/6/6 15:46
 * @Description
 */
@Module
public class CommonModule {

    //全局单例
    @Singleton
    @Provides
    public UserData provideBook() {
        return new UserData();
    }
}