package com.ylbl.cashpocket.base;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.TextView;

import com.ylbl.cashpocket.R;

import butterknife.BindView;

public abstract class BaseToolbarFmg extends BaseHttpFmg{
    @BindView(R.id.toolbar)
    protected Toolbar mToolbar;
    @BindView(R.id.tv_title)
    protected TextView mTvTitle;

    protected boolean isNativeToolbar;

    @Override
    protected void initToolBar() {
        super.initToolBar();
        if (mToolbar == null) {
            return;
        }
        if(!isNativeToolbar){
            mToolbar.setTitle("");
        }
    }

    @Override
    protected void initViewWithBack(boolean setBack) {
        super.initViewWithBack(setBack);
        if (!TextUtils.isEmpty(iniTitle())) {
            if(!isNativeToolbar){
                if (mTvTitle != null)
                    mTvTitle.setText(iniTitle());
            }else {
                mToolbar.setTitle(iniTitle());
            }

        }
        if (mToolbar != null) {

        }
    }



}
