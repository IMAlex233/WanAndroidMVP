package com.xlu.wanandroidmvp.module.mine;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.xlu.wanandroidmvp.http.api.WanAndroidService;
import com.xlu.wanandroidmvp.http.bean.Coin;
import com.xlu.wanandroidmvp.http.manger.NetWorkManager;
import com.xlu.wanandroidmvp.result.WanAndroidResponse;
import javax.inject.Inject;
import io.reactivex.Observable;


@FragmentScope
public class MineModel extends BaseModel implements MineContract.Model {

    @Inject
    Gson mGson;
    @Inject
    Application mApplication;
    @Inject
    WanAndroidService wanAndroidService;

    @Inject
    public MineModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<WanAndroidResponse<Coin>> personalCoin() {
        return NetWorkManager.getInstance().getWanAndroidService().personalCoin();
    }

}