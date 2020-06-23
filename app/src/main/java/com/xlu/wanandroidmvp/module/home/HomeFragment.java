package com.xlu.wanandroidmvp.module.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.base.BaseLazyLoadFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xlu.wanandroidmvp.R;
import com.xlu.wanandroidmvp.adapter.ArticleAdapter;
import com.xlu.wanandroidmvp.common.AppConfig;
import com.xlu.wanandroidmvp.common.Const;
import com.xlu.wanandroidmvp.common.ScrollTopListener;
import com.xlu.wanandroidmvp.di.component.DaggerHomeComponent;
import com.xlu.wanandroidmvp.event.Event;
import com.xlu.wanandroidmvp.http.bean.Article;
import com.xlu.wanandroidmvp.http.bean.ArticleInfo;
import com.xlu.wanandroidmvp.http.bean.BannerImg;
import com.xlu.wanandroidmvp.utils.SmartRefreshUtils;
import com.xlu.wanandroidmvp.utils.ToastUtils;
import com.xlu.wanandroidmvp.utils.WrapContentLinearLayoutManager;
import com.youth.banner.Banner;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;

public class HomeFragment extends BaseLazyLoadFragment<HomePresenter> implements HomeContract.View, ScrollTopListener {
    private String TAG = "HomeFraagment";

    Unbinder unbinder;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private ArticleAdapter adapter;

    private int page;
    private int pageCount;


    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerHomeComponent.builder().appComponent(appComponent).view(this).build().inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initRefreshLayout();
        initScrollList();
        lazyLoadData();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG,"onCreateView");
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    private void initScrollList() {
        assert mPresenter != null;
        adapter = new ArticleAdapter(new ArrayList<>(), ArticleAdapter.TYPE_COMMON);
        adapter.openLoadAnimation(AppConfig.getInstance().getRvAnim());
        LinearLayoutManager layoutManager = new WrapContentLinearLayoutManager(mContext);
        ArmsUtils.configRecyclerView(mRecyclerView, layoutManager);
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener((adapter, view, position) -> switchToWebPage(position));
        adapter.setLikeListener(new ArticleAdapter.LikeListener() {
            @Override
            public void liked(Article item, int adapterPosition) {
                mPresenter.collectArticle(item, adapterPosition);
            }

            @Override
            public void unLiked(Article item, int adapterPosition) {
                mPresenter.collectArticle(item, adapterPosition);
            }
        });
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Article article = HomeFragment.this.adapter.getData().get(position);
                switch (view.getId()) {
                    case R.id.tvAuthor:
                        //switchToUserPage(article);
                        break;
                    case R.id.tvType:
                        //switchTabPage(article);
                        break;
                    default:
                        break;
                }
            }
        });
        adapter.setOnLoadMoreListener(() -> {
            if (pageCount != 0 && pageCount == page + 1) {
                showMessage("不再加载更多");
                adapter.loadMoreEnd();
                return;
            }
            page++;
            mPresenter.requestArticle(page);
        }, mRecyclerView);
    }



    private void switchToWebPage(int position) {
//        Intent intent = new Intent(mContext, X5WebActivity.class);
//        Article article = adapter.getData().get(position);
//        intent.putExtra(Const.Key.KEY_WEB_PAGE_TYPE, WebActivity.TYPE_ARTICLE);
//        intent.putExtra(Const.Key.KEY_WEB_PAGE_DATA, article);
//        launchActivity(intent);
    }

    //freshlayout事件处理
    private void initRefreshLayout() {
        SmartRefreshUtils.with(refreshLayout)
                         .pureScrollMode()
                         .setRefreshListener(() -> {
                             if (mPresenter != null) {
                                 page = 0;
                                 mPresenter.requestArticle(page);
                             }
                         });
    }


    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showLoading() {
        //statusView.showLoading();
    }

    @Override
    public void hideLoading() {
        refreshLayout.finishRefresh();
        //statusView.showContent();
        if (adapter.isLoading()) {
            adapter.loadMoreComplete();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG,"onResume");
        hideLoading();
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ToastUtils.showShort(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
    }


    @Override
    public void showArticles(ArticleInfo articleInfo) {
        this.pageCount = articleInfo.getPageCount();
        List<Article> data = articleInfo.getDatas();
        adapter.addData(data);
        adapter.loadMoreComplete();
    }

    @Override
    public void refresh(List<Article> articleList) {
        //刷新，替换数据
        adapter.replaceData(articleList);
    }

    @Override
    public void showLoadMoreFail() {
        adapter.loadMoreFail();
    }


    @Override
    public void onPause() {
        Log.d(TAG,"onPause");
        super.onPause();
        hideLoading();
    }


    @Override
    protected void lazyLoadData() {
        //showLoading();
        page = 0;
        assert mPresenter != null;
        mPresenter.requestArticle(page);
    }

    @Override
    public void scrollToTop() {
        //RvScrollTopUtils.smoothScrollTop(mRecyclerView);
    }

    @Override
    public void scrollToTopRefresh() {
        //lazyLoadData();
    }

    /**
     * 登录成功
     */
    @Subscriber
    public void onLoginSuccess(Event event) {
        if (null != event && event.getEventCode() == Const.EventCode.LOGIN_SUCCESS) {
            lazyLoadData();
        }
    }

    /**
     * 退出登录
     */
    @Subscriber
    public void onLogout(Event event) {
        if (null != event && event.getEventCode() == Const.EventCode.LOG_OUT) {
            lazyLoadData();
        }
    }


    @Subscriber
    public void onArticleCollected(Event<Article> event) {
        if (null == event) {
            return;
        }
        if (event.getEventCode() == Const.EventCode.COLLECT_ARTICLE) {
            Article article = event.getData();
            for (Article item : adapter.getData()) {
                if (article.getId() == item.getId()) {
                    item.setCollect(article.isCollect());
                }
            }
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        Timber.e("%s 保存状态", this.getClass().getSimpleName());
        // 意外销毁时（屏幕方向切换、颜色模式改变等）保存状态
        outState.putBoolean(Const.Key.SAVE_INSTANCE_STATE, true);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void showError(String msg) {
        //statusView.showError(msg);
    }

    @Override
    public void showNoNetwork() {
        //statusView.showNoNetwork();
    }

    @Override
    public void onStop() {
        Log.d(TAG,"onStop");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Log.d(TAG,"onDestoryView");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG,"onDestory");
        super.onDestroy();
    }

    @Override
    public void onCollectSuccess(Article article, int position) {

    }

    @Override
    public void onCollectFail(Article article, int position) {
        adapter.restoreLike(position);
    }
}
