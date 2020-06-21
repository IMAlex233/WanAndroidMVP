package com.xlu.wanandroidmvp.module.home;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.xlu.wanandroidmvp.http.api.WanAndroidService;
import com.xlu.wanandroidmvp.result.WanAndroidResponse;
import com.xlu.wanandroidmvp.http.bean.Article;
import com.xlu.wanandroidmvp.http.bean.ArticleInfo;
import com.xlu.wanandroidmvp.http.manger.NetWorkManager;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

@FragmentScope
public class HomeModel extends BaseModel implements HomeContract.Model {

    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    private WanAndroidService wanAndroidService;

    @Inject
    public HomeModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
        wanAndroidService = NetWorkManager.getInstance().getWanAndroidService();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<WanAndroidResponse<ArticleInfo>> getArticle(int page) {
        return wanAndroidService.homeArticles(page);
    }


    @Override
    public Observable<WanAndroidResponse<List<Article>>> getTopArticles() {
        return wanAndroidService.topArticles();
    }

    @Override
    public Observable<WanAndroidResponse> collect(int id) {
        return wanAndroidService.collectInside(id);
    }

    @Override
    public Observable<WanAndroidResponse> unCollect(int id) {
        return wanAndroidService.unCollect(id);
    }


}