package com.ylbl.cashpocket.ui.nouse;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ylbl.cashpocket.R;
import com.ylbl.cashpocket.base.BaseToolBarAty;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 区域选择
 */
public class ChooseAreaAty extends BaseToolBarAty implements View.OnClickListener {
    @BindView(R.id.aty_area_3km_rb)
    RadioButton area_3km;
    @BindView(R.id.aty_area_city_rb)
    RadioButton area_city;
    @BindView(R.id.aty_area_province_rb)
    RadioButton area_pro;
    @BindView(R.id.aty_area_country_rb)
    RadioButton area_cou;

    private  AlertDialog dialog;
    @Override
    protected int getLayoutId() {
        return R.layout.aty_choose_area;
    }

    @Override
    protected String iniTitle() {
        return "选择范围";
    }

    @Override
    protected void initViewWithBack(boolean setBack) {
        super.initViewWithBack(setBack);
    }
    @OnClick({R.id.aty_area_confirm_btn})
    void onClink (View view){
        switch (view.getId()){
            case R.id.aty_area_confirm_btn:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                View view1 = LayoutInflater.from(this).inflate(R.layout.dialog_pay , null);
                builder.setView(view1);
                RadioGroup radioGroup = view1.findViewById(R.id.rg);
                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId){
                            case R.id.dialog_pay_dian_rb:
                                break;
                            case R.id.dialog_pay_ali_rb:
                                break;
                        }
                    }
                });
                TextView totalMonty = view1.findViewById(R.id.dialog_pay_money_show_tv);
                TextView dianRestMoney = view1.findViewById(R.id.dialog_dian_money_rest_tv);


                dialog = builder.create();
                dialog.show();

                break;
        }
    }

    @Override
    public void onClick(View v) {

    }
}
