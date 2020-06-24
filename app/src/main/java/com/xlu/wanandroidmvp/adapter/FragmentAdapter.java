package com.xlu.wanandroidmvp.adapter;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


import com.xlu.wanandroidmvp.module.home.HomeFragment;
import com.xlu.wanandroidmvp.module.mine.MineFragment;
import com.xlu.wanandroidmvp.module.qa.QAFragment;
import com.xlu.wanandroidmvp.module.wx.WxFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 在FragmentPagerAdapter的instantiateItem方法（这个方法会在ViewPager滑动状态变更时调用）中，
 *
 * 每个position所对应的Fragment只会添加一次到FragmentManager里面，也就是说，我们在Adapter中重写的getItem方法，
 * 它的参数position不会出现两次相同的值。
 *
 * 当Fragment被添加时，会给这个Fragment指定一个根据itemId来区分的tag，而这个itemId就是根据getItemId方法来获取的，
 * 默认就是当前页面的索引值。
 *
 * 关键点就一点，getItem 这个方法不是 get Fragment，其实称之为 create Fragment更为合适。
 * 原理参考鸿洋文章：https://mp.weixin.qq.com/s/MOWdbI5IREjQP1Px-WJY1Q
 *
 */
public class FragmentAdapter extends FragmentPagerAdapter {
    // 私有成员mFragments，加载页面碎片
    private List<Fragment> mFragments = new ArrayList<>();

    public FragmentAdapter(FragmentManager fragmentManager,List<Fragment> fragments) {
        super(fragmentManager);
        // 加载初始化Fragment
        mFragments = fragments;
/*        mFragments.add(HomeFragment.newInstance());
        mFragments.add(QAFragment.newInstance());
        mFragments.add(WxFragment.newInstance());
        mFragments.add(MineFragment.newInstance());*/
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = mFragments.get(0);
                break;
            case 1:
                fragment = mFragments.get(1);
                break;
            case 2:
                fragment = mFragments.get(2);
                break;
            case 3:
                fragment = mFragments.get(3);
                break;
            default:
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}

