package com.xlu.wanandroidmvp.di.module;

import com.xlu.wanandroidmvp.module.qa.QAContract;
import com.xlu.wanandroidmvp.module.qa.QAModel;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class QAModule {

    @Binds
    abstract QAContract.Model bindQAModel(QAModel model);
}