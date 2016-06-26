package org.sysu.sjk.gankclient.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.sysu.sjk.gankclient.Constant;
import org.sysu.sjk.gankclient.R;
import org.sysu.sjk.gankclient.base.BaseActivity;

import butterknife.BindView;

/**
 * 负责展示Gank内容的界面，是一个WebView
 * Created by sjk on 2016/6/26.
 */
public class GankDetailActivity extends BaseActivity {

    @BindView(R.id.web_gank_content)
    WebView webContent;

    @BindView(R.id.web_loading_progress_bar)
    ProgressBar webLoadingBar;

    @BindView(R.id.web_toolbar_title)
    TextView webTitle;

    @BindView(R.id.web_toolbar)
    Toolbar webToolbar;

    private String gankUrl;
    private String gankTitle;

    /**
     * 相当于直接打开网页的操作
     *
     * @param context
     * @param url
     */
    public static void start(Context context, String url, String title) {
        Intent intent = new Intent(context, GankDetailActivity.class);
        intent.putExtra(Constant.URL, url);
        intent.putExtra(Constant.TITLE, title);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_gank_detail;
    }

    @Override
    protected void bindViews() {
        Intent intent = getIntent();
        gankUrl = intent.getStringExtra(Constant.URL);
        gankTitle = intent.getStringExtra(Constant.TITLE);

        // 标题
        webTitle.setText(gankTitle);

        // 导航
        webToolbar.setBackgroundResource(R.color.primaryColor);
        setSupportActionBar(webToolbar);
        getSupportActionBar().setTitle(""); // 取消原来的标题，用TextView代替
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        webToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        webToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // WebView配置，包括和ProgressBar交互
        webContent.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String url) {
                if (url != null) {
                    webView.loadUrl(url);
                }
                return true;    // return true表示不会调用系统的浏览器打开
            }
        });
        webContent.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                webLoadingBar.setProgress(newProgress);
                if (newProgress < 100) {
                    // 未加载完，显示相应的进度
                    webLoadingBar.setVisibility(View.VISIBLE);
                } else {
                    // 加载完了就消失
                    webLoadingBar.setVisibility(View.GONE);
                }
            }
        });
        WebSettings settings = webContent.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        settings.setAppCacheEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); // 设置WebView的缓存
        webContent.loadUrl(gankUrl);
    }
}
