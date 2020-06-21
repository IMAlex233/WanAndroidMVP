package com.xlu.wanandroidmvp.di.common;

import com.xlu.wanandroidmvp.http.bean.UserData;
import javax.inject.Singleton;
import dagger.Component;

/**
 * @Author xlu
 * @Date 2020/6/6 15:47
 * @Description commonComponent中不提供inject方法，CommonComponent 并不是直接用来对应Activity 完成以依赖注入的，而是告诉依赖它的 Component 我可以给你提供什么依赖对象
 */
@Singleton
@Component(modules = {CommonModule.class})
public interface CommonComponent {
    UserData provideUserData();
}
