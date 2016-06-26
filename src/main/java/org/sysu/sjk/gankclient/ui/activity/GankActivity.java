package org.sysu.sjk.gankclient.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import org.sysu.sjk.gankclient.Constant;
import org.sysu.sjk.gankclient.R;
import org.sysu.sjk.gankclient.adapter.GankPagerAdapter;
import org.sysu.sjk.gankclient.base.BaseActivity;
import org.sysu.sjk.gankclient.ui.fragment.GankFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by sjk on 2016/6/25.
 */
public class GankActivity extends BaseActivity {

    @BindView(R.id.gank_tabs)
    TabLayout tabLayout;

    @BindView(R.id.gank_view_pager)
    ViewPager viewPager;

    FragmentPagerAdapter fragmentPagerAdapter;
    List<GankFragment> gankFragmentList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_gank;
    }

    @Override
    protected void bindViews() {
        gankFragmentList = new ArrayList<>();

        gankFragmentList.add(GankFragment.newInstance(Constant.TYPE_ANDROID));
        gankFragmentList.add(GankFragment.newInstance(Constant.TYPE_IOS));
        gankFragmentList.add(GankFragment.newInstance(Constant.TYPE_FRONT_END));
        gankFragmentList.add(GankFragment.newInstance(Constant.TYPE_RECOMMEND));
        gankFragmentList.add(GankFragment.newInstance(Constant.TYPE_EXTEND));
        //还有两种media型的...

        fragmentPagerAdapter = new GankPagerAdapter(getSupportFragmentManager(), gankFragmentList);
        viewPager.setAdapter(fragmentPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        configTabLayout();
    }

    private void configTabLayout() {
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setBackgroundColor(getResources().getColor(R.color.primaryColor));
        tabLayout.setTabTextColors(getResources().getColor(R.color.grey_300),
                getResources().getColor(R.color.bgTextColor));
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.accentColor));
    }
}
