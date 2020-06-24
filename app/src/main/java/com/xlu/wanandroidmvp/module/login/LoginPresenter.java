package com.xlu.wanandroidmvp.module.login;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.xlu.wanandroidmvp.base.BaseWanObserver;
import com.xlu.wanandroidmvp.common.AppConfig;
import com.xlu.wanandroidmvp.common.Const;
import com.xlu.wanandroidmvp.http.bean.UserData;
import com.xlu.wanandroidmvp.result.WanAndroidResponse;
import com.xlu.wanandroidmvp.utils.RxScheduler;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;


@ActivityScope
public class LoginPresenter extends BasePresenter<LoginContract.Model, LoginContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;

    @Inject
    public LoginPresenter(LoginContract.Model model, LoginContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    public void login(String userName, String password) {
        mModel.login(userName, password)
              .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
              .compose(RxScheduler.Obs_io_main())
              .subscribe(new BaseWanObserver<WanAndroidResponse<UserData>>(mRootView) {

                  @Override
                  public void onError(Throwable e) {
                      super.onError(e);
                      AppConfig.getInstance().setAccount("");
                      AppConfig.getInstance().setPassword("");
                      AppConfig.getInstance().setLogin(false);
                      mRootView.loginFailed();
                  }

                  @Override
                  public void onSuccess(WanAndroidResponse<UserData> result) {
                      if (result.getErrorCode() == Const.HttpConst.HTTP_CODE_SUCCESS) {
                          UserData user = result.getData();
                          if (user != null) {
                              AppConfig.getInstance().setAccount(userName);
                              AppConfig.getInstance().setPassword(password);

                              AppConfig.getInstance().setLogin(true);
                              AppConfig.getInstance().setUserName(user.getUsername());
                              mRootView.loginSuccess();
                          }
                      }
                      else {
                          mRootView.showMessage(result.getErrorMsg());
                      }
                  }
              });
    }
}
