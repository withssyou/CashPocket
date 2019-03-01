package com.ylbl.cashpocket.ui.center;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ylbl.cashpocket.R;
import com.ylbl.cashpocket.base.BaseToolBarAty;
import com.ylbl.cashpocket.bean.ConfigInfo;
import com.ylbl.cashpocket.bean.ResultInfo;
import com.ylbl.cashpocket.net.AppDbCtrl;
import com.ylbl.cashpocket.net.Constants;
import com.ylbl.cashpocket.utils.SpUtils;
import com.ylbl.cashpocket.utils.StringUtils;
import com.ylbl.cashpocket.utils.ToastUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 提现
 */
public class DrawCashAty extends BaseToolBarAty{
    @BindView(R.id.aty_deposit_money_tv)
    TextView restMoney;
    @BindView(R.id.aty_deposit_cash_extra_tv)
    TextView extraMoney;
    @BindView(R.id.aty_deposit_money_et)
    EditText cashMoney;
    private String money;
    private ConfigInfo configInfo;
    @Override
    protected int getLayoutId() {
        return R.layout.aty_deposit_money;
    }

    @Override
    protected String iniTitle() {
        return "提现";
    }

    @Override
    protected void initViewWithBack(boolean setBack) {
        super.initViewWithBack(true);
        Intent intent = getIntent();
        money = StringUtils.doubleToString(Double.parseDouble(intent.getStringExtra("money")));
        restMoney.setText("当前余额"+ money+"元");
        configInfo = SpUtils.getConfigInfo(context);
        if (configInfo.getWithdrawalType() .equals("2")){
            extraMoney.setText("手续费："+configInfo.getWithdrawalRate());
        }else {
            cashMoney.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.toString().length() > 0 ){
                        Double rate = Double.parseDouble(cashMoney.getText().toString().trim())* Double.parseDouble(configInfo.getWithdrawalRate());
                        DecimalFormat df = new DecimalFormat("0.00");
                        extraMoney.setText("手续费："+ df.format(rate) + "元");
                    }else {
                        extraMoney.setText("手续费："+ 0.00+"元");
                    }
                }
            });
        }
    }
    @OnClick({R.id.aty_deposit_all_tv , R.id.money_submit_btn})
    void onClick(View view){
        switch (view.getId()){
            case R.id.aty_deposit_all_tv:
                cashMoney.setText(money);
                if (configInfo.getWithdrawalType().equals("1")){
                    Double rate = Double.parseDouble(cashMoney.getText().toString().trim())* Double.parseDouble(configInfo.getWithdrawalRate());
                    DecimalFormat df = new DecimalFormat("0.00");
                    extraMoney.setText("手续费："+ df.format(rate));
                }
                break;
            case R.id.money_submit_btn:
                if (configInfo.getWithdrawalSwitch().equals("0")){
                    ToastUtils.toastShort(context , "暂不开放提现通道");
                }else {
                    Map<String , String > params = new HashMap<>();
                    params.put("url" , Constants.WITH_DRAW);
                    params.put("money" , cashMoney.getText().toString().trim());
                    newAsyncTaskExecute(Constants.HTTP_ACTION_1 , params);
                }
                break;
        }
    }

    @Override
    protected void doInBackgroundTask(int asyncid, Map params, Callback callback) {
        AppDbCtrl.doServer(asyncid , params , callback ,context);
    }

    @Override
    protected void onPostExecuteTask(int asyncid, ResultInfo resultInfo) {
        super.onPostExecuteTask(asyncid, resultInfo);
        switch (asyncid){
            case Constants.HTTP_ACTION_1:
                ToastUtils.toastShort(this , "提现操作成功 ，交与银行处理");
                setResult(0x20);
                finish();
                break;
        }
    }
}
