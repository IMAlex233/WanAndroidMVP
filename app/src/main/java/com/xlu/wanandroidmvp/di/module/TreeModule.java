package com.xlu.wanandroidmvp.di.module;

import com.xlu.wanandroidmvp.module.navi.KnowledgeModel;
import com.xlu.wanandroidmvp.module.navi.TreeContract;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class TreeModule {

    @Binds
    abstract TreeContract.Model bindTreeModel(KnowledgeModel model);
}