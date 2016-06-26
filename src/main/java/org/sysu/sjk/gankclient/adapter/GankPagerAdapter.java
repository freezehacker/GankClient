package org.sysu.sjk.gankclient.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import org.sysu.sjk.gankclient.Constant;
import org.sysu.sjk.gankclient.ui.fragment.GankFragment;
import org.sysu.sjk.gankclient.utils.LogUtils;

import java.util.List;

/**
 * Created by sjk on 2016/6/25.
 */
public class GankPagerAdapter extends FragmentPagerAdapter {

    List<GankFragment> fragments;

    public GankPagerAdapter(FragmentManager fm, List<GankFragment> inFragments) {
        super(fm);
        fragments = inFragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    /**
     * 该函数回调的时候，Fragment还没有调用onCreate，只是以无状态的形式加入了一个列表中
     * 所以不能用getType来取到Fragment类型
     * @param position
     * @return  Fragment在TabLayout上的标题
     */
    @Override
    public CharSequence getPageTitle(int position) {
        //return fragments.get(position).getType();
        return Constant.TYPES[position];
    }
}
