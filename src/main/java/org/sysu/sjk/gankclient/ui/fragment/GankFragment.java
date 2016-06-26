package org.sysu.sjk.gankclient.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.sysu.sjk.gankclient.Constant;
import org.sysu.sjk.gankclient.R;
import org.sysu.sjk.gankclient.base.BaseFragment;
import org.sysu.sjk.gankclient.bean.DailyGank;
import org.sysu.sjk.gankclient.bean.Gank;
import org.sysu.sjk.gankclient.bean.TypeGank;
import org.sysu.sjk.gankclient.network.GankClient;
import org.sysu.sjk.gankclient.ui.activity.GankDetailActivity;
import org.sysu.sjk.gankclient.ui.widget.MightyRecyclerView;
import org.sysu.sjk.gankclient.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by sjk on 2016/6/25.
 */
public class GankFragment extends BaseFragment {

    private String mType;
    private int mPage = 1;  // 默认从第1页开始

    @BindView(R.id.mighty_rv)
    MightyRecyclerView recycler;

    private List<Gank> gankList = new ArrayList<>();

    /**
     * 根据类型，创建fragment
     *
     * @param type 干货类型，有Android iOS等
     * @return
     */
    public static GankFragment newInstance(String type) {
        GankFragment ret = new GankFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.TYPE, type);
        ret.setArguments(bundle);
        return ret;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_gank_universal;
    }

    public String getType() {
        return mType;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 从bundle中取得fragment的类型
        mType = getArguments().getString(Constant.TYPE);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // step 1
        recycler.setListRef(gankList);

        // step 2
        recycler.setOnItemClickListener(new MightyRecyclerView.GankListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Gank gank = gankList.get(position);
                GankDetailActivity.start(getActivity(), gank.getUrl(), gank.getDesc());
            }
        });
        recycler.setOnLoadMoreListener(new MightyRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                GankClient.getInstance().getTypeGank(mType, mPage)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<TypeGank>() {
                            @Override
                            public void call(TypeGank typeGank) {
                                List<Gank> ganks = typeGank.getResults();
                                if (ganks == null || ganks.size() == 0) {
                                    LogUtils.log("no more");
                                } else {
                                    Toast.makeText(getActivity(), "Load OK!", Toast.LENGTH_SHORT).show();
                                    recycler.loadMoreItems(ganks);
                                    ++mPage;    // 下次要加载下一页
                                }
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                LogUtils.log(throwable.getMessage());
                            }
                        });
            }
        });
        recycler.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GankClient.getInstance().getTypeGank(mType, mPage)
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<TypeGank>() {
                            @Override
                            public void call(TypeGank typeGank) {
                                List<Gank> ganks = typeGank.getResults();
                                if (ganks == null || ganks.size() == 0) {
                                    LogUtils.log("empty");
                                } else {
                                    recycler.closeRefresh();
                                    Toast.makeText(getActivity(), "Refresh!", Toast.LENGTH_SHORT).show();
                                    recycler.refreshItems(ganks);
                                    mPage = 1;  // 重新回到第1页
                                }
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                recycler.closeRefresh();
                                LogUtils.log(throwable.getMessage());
                            }
                        });
            }
        });
        recycler.setRefreshingColors(R.color.primaryColor);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        /**
         * 一开始就刷新
         */
        recycler.autoRefreshInTheBeginning();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
