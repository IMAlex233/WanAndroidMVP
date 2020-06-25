package com.xlu.wanandroidmvp.module.navi;

import android.app.Application;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.xlu.wanandroidmvp.base.BaseWanObserver;
import com.xlu.wanandroidmvp.http.bean.Tab;
import com.xlu.wanandroidmvp.result.WanAndroidResponse;
import com.xlu.wanandroidmvp.utils.RxScheduler;

import java.util.List;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

@FragmentScope
public class KnowledgePresenter extends BasePresenter<TreeContract.Model, TreeContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;


    @Inject
    public KnowledgePresenter(TreeContract.Model model, TreeContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    public void requestTreeData() {
        mModel.getTreeData()
              .compose(RxScheduler.Obs_io_main())
              .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
              .subscribe(new BaseWanObserver<WanAndroidResponse<List<Tab>>>(mRootView) {

                  @Override
                  public void onSuccess(WanAndroidResponse<List<Tab>> response) {
                      List<Tab> tabs = response.getData();
                      mRootView.showTreeData(tabs);
                  }
              });
    }
}
