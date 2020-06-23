package com.xlu.wanandroidmvp.module.main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import android.view.MenuItem;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.xlu.wanandroidmvp.R;
import com.xlu.wanandroidmvp.adapter.FragmentAdapter;
import com.xlu.wanandroidmvp.di.component.DaggerMainComponent;


import butterknife.BindView;
import timber.log.Timber;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {

    @BindView(R.id.nav_view)
    BottomNavigationView navView;

    @BindView(R.id.viewpager)
    ViewPager viewPager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMainComponent.builder().appComponent(appComponent).view(this).build().inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initNavView();


    }

    private void initNavView() {
        // 去除背景底色
        navView.setItemIconTintList(null);
        // 实例化adapter，得到fragment
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager());
        // 建立连接
        viewPager.setAdapter(fragmentAdapter);

        setNavigation();
    }

    /**
     * 设置底部导航栏
     */
    public void setNavigation() {

        // 底部导航栏点击事件
        navView.setOnNavigationItemSelectedListener(menuItem -> {
            //setToolbar(menuItem.getTitle().toString());
            switch (menuItem.getItemId()) {
                case R.id.navigation_home:
                    viewPager.setCurrentItem(0);
                    break;
                case R.id.navigation_qa:
                    viewPager.setCurrentItem(1);
                    break;
                case R.id.navigation_wx:
                    viewPager.setCurrentItem(2);
                    break;
                case R.id.navigation_mine:
                    viewPager.setCurrentItem(3);
                    break;
                default:
                    break;
            }
            return false;
        });

        //viewpager监听事件，当viewpager滑动时得到对应的fragment碎片
        viewPager.setOffscreenPageLimit(3);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                MenuItem menuItem = navView.getMenu().getItem(position);
                menuItem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void showMessage(@NonNull String message) {

    }


}