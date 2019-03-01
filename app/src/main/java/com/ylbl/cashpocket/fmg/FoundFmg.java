package com.ylbl.cashpocket.fmg;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ylbl.cashpocket.R;
import com.ylbl.cashpocket.base.BaseToolbarFmg;
import com.ylbl.cashpocket.utils.StatusBarUtil;
import com.ylbl.cashpocket.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class FoundFmg extends BaseToolbarFmg{
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_pocket;
    }

    @Override
    protected String iniTitle() {
        return "";
    }

    @Override
    protected void initViewWithBack(boolean setBack, Bundle savedInstanceState) {
        super.initViewWithBack(setBack, savedInstanceState);
    }

    @OnClick({R.id.frag_found_mail_tv,R.id.frag_found_conner_tv,R.id.frag_found_samecity_tv,R.id.frag_found_show_tv,
            R.id.frag_found_city_tv,R.id.frag_found_game_tv,R.id.frag_found_quan_tv,R.id.frag_found_change_tv,
            R.id.frag_found_money_tv})
    void onClick(View view){
        switch (view.getId()){
            case R.id.frag_found_mail_tv:
//                break;
            case R.id.frag_found_conner_tv:
//                break;
            case R.id.frag_found_samecity_tv:
//                break;
            case R.id.frag_found_show_tv:
//                break;
            case R.id.frag_found_city_tv:
//                break;
            case R.id.frag_found_game_tv:
//                break;
            case R.id.frag_found_quan_tv:
//                break;
            case R.id.frag_found_change_tv:
//                break;
            case R.id.frag_found_money_tv:
                ToastUtils.toastLong(context , "该功能尚未开放，敬请关注");
                break;

        }
    }
}
