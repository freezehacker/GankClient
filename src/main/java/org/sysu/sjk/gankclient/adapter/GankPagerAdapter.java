package org.sysu.sjk.gankclient.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import org.sysu.sjk.gankclient.ui.fragment.GankFragment;

import java.util.List;

/**
 * Created by sjk on 2016/6/25.
 */
public class GankPagerAdapter extends FragmentPagerAdapter {

    List<GankFragment> fragments;

    public GankPagerAdapter(FragmentManager fm, List<GankFragment> inFragments) {
        super(fm);
        fragments=inFragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragments.get(position).getType();
    }
}
