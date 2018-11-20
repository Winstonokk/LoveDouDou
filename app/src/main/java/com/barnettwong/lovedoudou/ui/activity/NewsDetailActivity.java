package com.barnettwong.lovedoudou.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.barnettwong.lovedoudou.R;
import com.barnettwong.lovedoudou.ui.view.TitleBar;
import com.jaydenxiao.common.base.BaseActivity;

/**
 * Created by wangfeng on 2018/8/10.
 * 资讯详情页
 **/
public class NewsDetailActivity extends BaseActivity {
    private WebView mAgentWebView;
    private TitleBar titleBar;
    private ProgressBar mProgressBar;
    private String detailUrl;

    @Override
    public int getLayoutId() {
        return R.layout.activity_news_detail;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        initIntexntData();

        titleBar = (TitleBar) findViewById(R.id.titleBar);
        titleBar.setImmersive(false);
        titleBar.setTitle(getString(R.string.str_news_detail_title));
        titleBar.setTitleColor(getResources().getColor(R.color.white));
        titleBar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        titleBar.setLeftImageResource(R.mipmap.back);
        titleBar.setLeftClickListener(v -> finish());
        mProgressBar=findViewById(R.id.activity_web_progressbar);
        mAgentWebView= findViewById(R.id.ll_web_content);
        setWebSetting();
        mAgentWebView.loadUrl(detailUrl);

    }

    private void setWebSetting() {
        mAgentWebView.setWebViewClient(new WebViewClient() {
            @SuppressWarnings("deprecation")
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }


            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                Log.e("wangfeng", "onReceivedError=errorCode:" + errorCode+","+description);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });

        mAgentWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress >= 50) {
                    mProgressBar.setVisibility(View.GONE);
                } else {
                    if (View.GONE == mProgressBar.getVisibility()) {
                        mProgressBar.setVisibility(View.VISIBLE);
                    }
                    mProgressBar.setProgress(newProgress);

                }
                Log.e("wangfeng", "网页进度=" + newProgress);
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                Log.e("wangfeng", "网页TITLE=" + title);
//                titleBar.setTitle(title);
            }

        });
    }

    private void initIntexntData() {
        Intent intent=getIntent();
        if(null==intent){
            return;
        }
        detailUrl=intent.getStringExtra("detailUrl");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyWebView(mAgentWebView);
    }

    public void destroyWebView(WebView mWebView){
        if(mWebView != null) {
            mWebView.clearHistory();
            mWebView.clearCache(true);
            mWebView.loadUrl("about:blank"); // clearView() should be changed to loadUrl("about:blank"), since clearView() is deprecated now
            mWebView.freeMemory();
            mWebView.pauseTimers();
            mWebView = null; // Note that mWebView.destroy() and mWebView = null do the exact same thing
        }
    }

}
