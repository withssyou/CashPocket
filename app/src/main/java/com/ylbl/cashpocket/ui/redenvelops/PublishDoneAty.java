package com.ylbl.cashpocket.ui.redenvelops;

import android.view.View;

import com.ylbl.cashpocket.R;
import com.ylbl.cashpocket.base.BaseToolBarAty;

import butterknife.OnClick;

/**
 * 红包发布完成
 */
public class PublishDoneAty extends BaseToolBarAty {

    @Override
    protected int getLayoutId() {
        return R.layout.aty_publish_done;
    }

    @Override
    protected void initViewWithBack(boolean setBack) {
        super.initViewWithBack(true);

    }

    @Override
    protected String iniTitle() {
        return "发红包";
    }

    @OnClick({R.id.aty_publish_done_btn})
    void onClick(View view){
        switch (view.getId()){
            case R.id.aty_publish_done_btn:
                finish();
                break;
        }
    }
}
