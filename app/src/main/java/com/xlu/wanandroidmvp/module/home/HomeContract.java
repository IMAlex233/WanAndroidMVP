package com.xlu.wanandroidmvp.module.home;

import com.jess.arms.mvp.IModel;
import com.xlu.wanandroidmvp.common.ICollectView;
import com.xlu.wanandroidmvp.result.WanAndroidResponse;
import com.xlu.wanandroidmvp.http.bean.Article;
import com.xlu.wanandroidmvp.http.bean.ArticleInfo;

import java.util.List;

import io.reactivex.Observable;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/01/2019 11:33
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface HomeContract {

    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends ICollectView {

        void showMoreArticles(ArticleInfo articleInfo);

        void refresh(List<Article> articleList);

        void showLoadMoreFail();

    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        Observable<WanAndroidResponse<ArticleInfo>> getArticle(int page);

        Observable<WanAndroidResponse<List<Article>>> getTopArticles();

        Observable<WanAndroidResponse> collect(int id);

        Observable<WanAndroidResponse> unCollect(int id);

    }
}
