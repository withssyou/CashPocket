package com.ylbl.cashpocket.ui.center;

import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.ylbl.cashpocket.R;
import com.ylbl.cashpocket.base.BaseToolBarAty;
import com.ylbl.cashpocket.fmg.DoingFmg;
import com.ylbl.cashpocket.fmg.DoneFmg;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 提现记录
 */
public class DrawCashRecordAty extends BaseToolBarAty{

    @BindView(R.id.aty_ad_lord_doing_tv)
    TextView doing;
    @BindView(R.id.aty_ad_lord_doing_v)
    View doing_v;
    @BindView(R.id.aty_ad_lord_done_tv)
    TextView done;
    @BindView(R.id.aty_ad_lord_done_v)
    View done_v;
    private Fragment doneFmg , doingFmg;
    private FragmentManager supportFragmentManager;
    private FragmentTransaction fragmentTransaction;
    @Override
    protected int getLayoutId() {
        return R.layout.aty_cash_draw_record;
    }

    @Override
    protected String iniTitle() {
        return "提现记录";
    }

    @Override
    protected void initViewWithBack(boolean setBack) {
        super.initViewWithBack(true);
        supportFragmentManager = getSupportFragmentManager();
        fragmentTransaction = supportFragmentManager.beginTransaction();
        doingFmg = new DoingFmg();
        fragmentTransaction.add(R.id.aty_ad_lord_fl, doingFmg).commit();
//
    }
//        已完成 和  未完成
    @OnClick({R.id.aty_ad_lord_doing_ll,R.id.aty_ad_lord_done_ll})
    void onClick(View view){
        hideFrag();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        switch (view.getId()){
            case R.id.aty_ad_lord_doing_ll:
                doing.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                done.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                doing_v.setBackgroundColor(getResources().getColor(R.color.colorRed));
                done_v.setBackgroundColor(getResources().getColor(R.color.white));

                fragmentTransaction.show(doingFmg).commit();

                break;
            case R.id.aty_ad_lord_done_ll:
                doing.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                done.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                done_v.setBackgroundColor(getResources().getColor(R.color.colorRed));
                doing_v.setBackgroundColor(getResources().getColor(R.color.white));
                if (doneFmg == null) {
                    doneFmg = new DoneFmg();
                    fragmentTransaction.add(R.id.aty_ad_lord_fl, doneFmg).commit();
                } else {
                    fragmentTransaction.show(doneFmg).commit();
                }
                break;
        }
    }
    /**
     * fragment切换时隐藏
     */
    private void hideFrag() {
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        if (doneFmg != null && doneFmg.isAdded()) {
            fragmentTransaction.hide(doneFmg);
        }
        if (doingFmg != null && doingFmg.isAdded()) {
            fragmentTransaction.hide(doingFmg);
        }
        fragmentTransaction.commit();
    }
}
