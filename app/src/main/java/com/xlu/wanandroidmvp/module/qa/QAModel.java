package com.xlu.wanandroidmvp.module.qa;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.xlu.wanandroidmvp.http.bean.ArticleInfo;
import com.xlu.wanandroidmvp.http.manger.NetWorkManager;
import com.xlu.wanandroidmvp.result.WanAndroidResponse;

import javax.inject.Inject;

import io.reactivex.Observable;

@FragmentScope
public class QAModel extends BaseModel implements QAContract.Model {

    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public QAModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<WanAndroidResponse<ArticleInfo>> getQAList(int page) {
        return NetWorkManager.getInstance().getWanAndroidService().qaList(page);
    }
}