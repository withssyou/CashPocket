package com.ylbl.cashpocket.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ylbl.cashpocket.utils.ToastUtils;

import butterknife.ButterKnife;

public abstract class BaseFmg extends Fragment {
    protected Activity context;
    protected Intent intent = new Intent();

    protected abstract int getLayoutId();

    protected String iniTitle() {
        return null;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = (Activity) context;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;
//        try {
            view = inflater.inflate(getLayoutId(), container, false);
            ButterKnife.bind(this, view);
            setMonitor();
            initToolBar();
            initViewWithBack(false,savedInstanceState);

//        } catch (Exception e) {
//            ToastUtils.toastShort(context,e.getMessage());
//        }
        return view;
    }

    protected void setMonitor() {

    }

    protected void initToolBar() {
    }

    protected void initViewWithBack(boolean setBack,Bundle savedInstanceState) {
        initViewWithBack(setBack);
    }

    protected void initViewWithBack(boolean setBack) {
    }

}
