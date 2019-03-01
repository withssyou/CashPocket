package com.ylbl.cashpocket.ui.nouse;

import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ylbl.cashpocket.R;
import com.ylbl.cashpocket.base.BaseToolBarAty;

import butterknife.BindView;

public class LordAty extends BaseToolBarAty{
    @BindView(R.id.aty_lord_wv)
    WebView webView;
    private String url = "http://www.baidu.com";
    @Override
    protected int getLayoutId() {
        return R.layout.aty_lord;
    }

    @Override
    protected String iniTitle() {
        return "领主说明";
    }

    @Override
    protected void initViewWithBack(boolean setBack) {
        super.initViewWithBack(true);

        WebSettings webSettings=webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);//不使用缓存，只从网络获取数据.
        //支持屏幕缩放
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webView.setWebChromeClient(new WebChromeClient(){
        });
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }
        });
        webView.loadUrl(url);
    }
}
