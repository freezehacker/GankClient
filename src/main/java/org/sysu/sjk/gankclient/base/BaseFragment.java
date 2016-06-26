package org.sysu.sjk.gankclient.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import rx.Subscription;

/**
 * Created by sjk on 2016/6/24.
 */
public abstract class BaseFragment extends Fragment {

    protected View mView;
    protected Subscription mSubscriptionRefresh, mSubscriptionLoadMore;

    protected abstract int getLayoutId();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(getLayoutId(), container, false);
        /**
         * 在View上用ButterKnife绑定控件
         */
        ButterKnife.bind(this, mView);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        if (mSubscriptionLoadMore != null) {
            mSubscriptionLoadMore.unsubscribe();
            mSubscriptionLoadMore = null;
        }
        if (mSubscriptionRefresh != null) {
            mSubscriptionRefresh.unsubscribe();
            mSubscriptionRefresh = null;
        }
        super.onDestroy();
    }
}
