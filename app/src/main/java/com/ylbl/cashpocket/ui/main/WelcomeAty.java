package com.ylbl.cashpocket.ui.main;

import android.content.Intent;
import android.os.Handler;

import com.ylbl.cashpocket.R;
import com.ylbl.cashpocket.base.BaseToolBarAty;
import com.ylbl.cashpocket.utils.StatusBarUtil;


public class WelcomeAty extends BaseToolBarAty{

    @Override
    protected int getLayoutId() {
        return R.layout.aty_welcome;
    }

    @Override
    protected void initViewWithBack(boolean setBack) {
        super.initViewWithBack(setBack);
        Handler handler  = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(WelcomeAty.this,MainAty.class));
                finish();
            }
        },2000);
    }

    @Override
    protected void onStart() {
        super.onStart();
        StatusBarUtil.setWindowStatusBarColor(this,R.color.colorBlueBar);
    }
}
