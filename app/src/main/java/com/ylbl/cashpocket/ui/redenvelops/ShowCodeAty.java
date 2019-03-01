package com.ylbl.cashpocket.ui.redenvelops;

import android.content.Intent;
import android.net.http.SslError;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.ylbl.cashpocket.R;
import com.ylbl.cashpocket.base.BaseToolBarAty;

public class ShowCodeAty extends BaseToolBarAty{
//    @BindView(R.id.aty_show_code_iv)
//    ImageView imageView;
//    @BindView(R.id.aty_show_code)
//    WebView webView;

    @Override
    protected int getLayoutId() {
        return R.layout.aty_show_code;
    }

    @Override
    protected String iniTitle() {
        return "支付码";
    }

    @Override
    protected void initViewWithBack(boolean setBack) {
        super.initViewWithBack(true);

    }
}
