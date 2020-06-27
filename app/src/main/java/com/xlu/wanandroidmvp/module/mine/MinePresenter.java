package com.xlu.wanandroidmvp.module.mine;

import android.app.Application;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.xlu.wanandroidmvp.base.BaseWanObserver;
import com.xlu.wanandroidmvp.common.AppConfig;
import com.xlu.wanandroidmvp.http.bean.Coin;
import com.xlu.wanandroidmvp.result.WanAndroidResponse;
import com.xlu.wanandroidmvp.utils.RxScheduler;
import javax.inject.Inject;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@FragmentScope
public class MinePresenter extends BasePresenter<MineContract.Model, MineContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;

    @Inject
    public MinePresenter(MineContract.Model model, MineContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    public void loadCoin() {
        mModel.personalCoin()
                .retryWhen(new RetryWithDelay(5, 3))
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .compose(RxScheduler.Obs_io_main())
                .subscribe(new BaseWanObserver<WanAndroidResponse<Coin>>(mRootView) {
                    @Override
                    public void onSuccess(WanAndroidResponse<Coin> response) {
                        mRootView.showCoin(response.getData());
                    }
                });
    }



}
