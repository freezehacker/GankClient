package org.sysu.sjk.gankclient.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.sysu.sjk.gankclient.Constant;
import org.sysu.sjk.gankclient.R;
import org.sysu.sjk.gankclient.base.BaseFragment;

import butterknife.ButterKnife;

/**
 * Created by sjk on 2016/6/25.
 */
public class GankFragment extends BaseFragment {

    private String mType;
    private View mView;

    /**
     * 根据类型，创建fragment
     * @param type  干货类型，有Android iOS等
     * @return
     */
    public static GankFragment newInstance(String type) {
        GankFragment ret = new GankFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.TYPE, type);
        ret.setArguments(bundle);
        return ret;
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (null == mView) {
            mView = inflater.inflate(R.layout.fragment_gank_universal, container, false);
            /**
             * 在View上用ButterKnife绑定控件
             */
            ButterKnife.bind(this, mView);
        }
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
    public void onDestroyView() {
        super.onDestroyView();
    }
}
