package org.sysu.sjk.gankclient.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.sysu.sjk.gankclient.Constant;
import org.sysu.sjk.gankclient.R;
import org.sysu.sjk.gankclient.base.BaseActivity;

import butterknife.BindView;

/**
 * 负责展示Gank内容的界面，是一个WebView
 * Created by sjk on 2016/6/26.
 */
public class GankDetailActivity extends BaseActivity {

    @BindView(R.id.wv_gank_content)
    WebView wvGankContent;

    private String gankUrl;

    public static void start(Context context, String url) {
        Intent intent = new Intent(context, GankDetailActivity.class);
        intent.putExtra(Constant.URL, url);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_gank_detail;
    }

    @Override
    protected void bindViews() {
        gankUrl = getIntent().getStringExtra(Constant.URL);

        wvGankContent.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String url) {
                webView.loadUrl(url);
                return true;    // return true表示不会调用系统的浏览器打开
            }
        });
        wvGankContent.getSettings().setJavaScriptEnabled(true);

        wvGankContent.loadUrl(gankUrl);
    }
}
