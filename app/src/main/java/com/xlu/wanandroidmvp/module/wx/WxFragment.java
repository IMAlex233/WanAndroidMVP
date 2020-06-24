package com.xlu.wanandroidmvp.module.wx;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.didichuxing.doraemonkit.ui.base.BaseFragment;
import com.flyco.tablayout.SlidingTabLayout;
import com.google.android.material.tabs.TabLayout;
import com.jess.arms.base.BaseLazyLoadFragment;
import com.jess.arms.di.component.AppComponent;
import com.xlu.wanandroidmvp.R;
import com.xlu.wanandroidmvp.adapter.FragmentAdapter;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WxFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WxFragment extends Fragment {


    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    private ArrayList<Fragment> mFragments;
    private static final String mTitles[] = {"体系","导航"};

    public static WxFragment newInstance() {
        return new WxFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wx, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }


    private void init() {
        mFragments = new ArrayList<>();
        OneFragment oneFragment =  OneFragment.newInstance();
        TwoFragment twoFragment =  TwoFragment.newInstance();
        mFragments.add(oneFragment);
        mFragments.add(twoFragment);

        // 设置TAB滚动显示
        //tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        // 设置选中下划线颜色
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorPrimary));
        // 设置文本字体颜色[未选中颜色、选中颜色]
        tabLayout.setTabTextColors(getResources().getColor(R.color.green_grass),
                getResources().getColor(R.color.colorPrimary));
        // 设置下划线高度，已弃用，建议在XML中使用app:tabIndicatorHeight属性设置
        tabLayout.setSelectedTabIndicatorHeight(10);
        // 设置下划线跟文本宽度一致
        tabLayout.setTabIndicatorFullWidth(true);
        // 设置TabLayout和ViewPager绑定
        tabLayout.setupWithViewPager(viewPager, false);
        // 添加TAB标签
        for (String mTitle : mTitles) {
            tabLayout.addTab(tabLayout.newTab().setText(mTitle));
        }

        viewPager.setAdapter(new FragmentAdapter(getParentFragmentManager(), tabLayout.getTabCount()));
        // 设置ViewPager默认显示index
        viewPager.setCurrentItem(0);


        // 调用系统API设置ICON
        for (int i = 0; i < mTitles.length; i++) {
            tabLayout.getTabAt(i).setIcon(getResources().getDrawable(R.drawable.ic_baseline_account_balance_24));
        }

        // 自定义Tab文本和ICON
//        for (int i = 0; i < mTitles.length; i++) {
//            tabLayout.getTabAt(i).setCustomView(setCustomTab(i));
//        }

    }

    class FragmentAdapter extends FragmentPagerAdapter {

        public FragmentAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }


}