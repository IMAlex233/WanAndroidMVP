package com.xlu.wanandroidmvp.module.home;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.xlu.wanandroidmvp.base.BaseWanObserver;
import com.xlu.wanandroidmvp.common.CollectHelper;
import com.xlu.wanandroidmvp.http.bean.BannerImg;
import com.xlu.wanandroidmvp.result.WanAndroidResponse;
import com.xlu.wanandroidmvp.http.bean.Article;
import com.xlu.wanandroidmvp.http.bean.ArticleInfo;
import com.xlu.wanandroidmvp.http.manger.RetryWithDelay;
import com.xlu.wanandroidmvp.utils.RxScheduler;

import java.util.List;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;


@FragmentScope
public class HomePresenter extends BasePresenter<HomeContract.Model, HomeContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    ImageLoader mImageLoader;

    @Inject
    HomePresenter(HomeContract.Model model, HomeContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mImageLoader = null;
    }

    public void requestArticle(int page) {
        mModel.getArticle(page)
              .compose(RxScheduler.Obs_io_main())
              .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
              .retryWhen(new RetryWithDelay(1000L))
              .subscribe(new BaseWanObserver<WanAndroidResponse<ArticleInfo>>(mRootView) {

                  @Override
                  public void onSuccess(WanAndroidResponse<ArticleInfo> response) {
                      ArticleInfo info = response.getData();
                      mRootView.showArticles(info);
                  }

                  @Override
                  public void onError(Throwable e) {
                      super.onError(e);
                      mRootView.showLoadMoreFail();
                  }

                  @Override
                  protected void onException(ExceptionReason reason) {
                      super.onException(reason);
                      mRootView.showLoadMoreFail();
                  }

                  @Override
                  public void onComplete() {
                      mRootView.hideLoading();
                  }


              });

    }


/*    public void requestHomeData() {
        //使用zip合并首页三个创建网络访问的observable
        Observable.zip(mModel.getTopArticles(), mModel.getArticle(0),
                (topResponse, commonResponse) -> {
                    List<Article> topArticles = topResponse.getData();
                    for (Article article : topArticles) {
                        article.setTop(true);
                    }
                    List<Article> articleList = commonResponse.getData().getDatas();
                    topArticles.addAll(articleList);

                    return topArticles;
                })
                .compose(RxScheduler.Obs_io_main())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .retryWhen(new RetryWithDelay(10, 2000))
                .doOnNext(response -> {

                    List<Article> articles = response;

                    if (ObjectUtils.isNotEmpty(articles)) {
                        mRootView.refresh(articles);
                    }
                })
                .doOnComplete(() -> mRootView.hideLoading())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResourceObserver<ResponseBody>() {
                    @Override
                    protected void onStart() {
                        if (!NetWorkManager.isNetWorkAvailable()) {
                            mRootView.showNoNetwork();
                            dispose();
                        }
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        String url = null;
                        try {
                            url = responseBody.string();
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                        mRootView.addDailyPic(url);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        mRootView.hideLoading();
                    }
                });
    }
    */

    /**
     * 收藏或取消收藏文章
     */
    public void collectArticle(Article article, int position) {
        CollectHelper.with(mRootView).target(article).position(position).collect();
    }

    /**
     * 合并首页banner及文章列表的实体
     */
    private static class ZipEntity {

        private WanAndroidResponse<List<BannerImg>> bannerResponse;

        private WanAndroidResponse<List<Article>> articleResponse;

        public ZipEntity(WanAndroidResponse<List<BannerImg>> bannerResponse, WanAndroidResponse<List<Article>> articleResponse) {
            this.bannerResponse = bannerResponse;
            this.articleResponse = articleResponse;
        }

        public WanAndroidResponse<List<BannerImg>> getBannerResponse() {
            return bannerResponse;
        }

        public void setBannerResponse(WanAndroidResponse<List<BannerImg>> bannerResponse) {
            this.bannerResponse = bannerResponse;
        }

        public WanAndroidResponse<List<Article>> getArticleResponse() {
            return articleResponse;
        }

        public void setArticleResponse(WanAndroidResponse<List<Article>> articleResponse) {
            this.articleResponse = articleResponse;
        }
    }

}
