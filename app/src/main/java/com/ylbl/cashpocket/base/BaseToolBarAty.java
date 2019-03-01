package com.ylbl.cashpocket.base;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.ylbl.cashpocket.R;
import com.ylbl.cashpocket.utils.ToastUtils;

import butterknife.BindView;

public abstract class BaseToolBarAty extends BaseHttpAty {
    @BindView(R.id.toolbar)
    protected Toolbar mToolbar;
    @BindView(R.id.tv_title)
    protected TextView mTvTitle;
    @BindView(R.id.bar_back)
    protected Button back;

    protected boolean isHome;

    @Override
    protected void initToolBar() {
        super.initToolBar();
//        getSupportActionBar().setDisplayShowTitleEnabled(true);
        mToolbar.setTitle("");
    }
    @Override
    protected void initViewWithBack(boolean setBack) {
        super.initViewWithBack(setBack);
        if (!TextUtils.isEmpty(iniTitle())) {
            if (mTvTitle != null)
                mTvTitle.setText(iniTitle());
        }
        if (mToolbar != null) {
            if (setBack) {
                back.setVisibility(View.VISIBLE);
                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        doToolBarNavigationClick();
                    }
                });
            }
        }
    }
    /**
     * 通过设置全屏，设置状态栏透明
     *
     * @param activity
     */
    @Override
    protected void fullScreen(Activity activity) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                //5.x开始需要把颜色设置透明，否则导航栏会呈现系统默认的浅灰色
//                Window window = activity.getWindow();
//                View decorView = window.getDecorView();
//                //两个 flag 要结合使用，表示让应用的主体内容占用系统状态栏的空间
//                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//                decorView.setSystemUiVisibility(option);
//                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//                window.setStatusBarColor(Color.TRANSPARENT);
//                //导航栏颜色也可以正常设置
////                window.setNavigationBarColor(Color.TRANSPARENT);
//            } else {
//                Window window = activity.getWindow();
//                WindowManager.LayoutParams attributes = window.getAttributes();
//                int flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
//                int flagTranslucentNavigation = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
//                attributes.flags |= flagTranslucentStatus;
////                attributes.flags |= flagTranslucentNavigation;
//                window.setAttributes(attributes);
//            }
//        }
    }
    protected void doToolBarNavigationClick() {
        finish();
    }

}
