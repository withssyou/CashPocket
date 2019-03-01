package com.ylbl.cashpocket.ui.main;

import android.content.Intent;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ylbl.cashpocket.R;
import com.ylbl.cashpocket.base.BaseToolBarAty;
import com.ylbl.cashpocket.bean.ResultInfo;
import com.ylbl.cashpocket.net.AppDbCtrl;
import com.ylbl.cashpocket.net.Constants;
import com.ylbl.cashpocket.ui.main.LoginAty;
import com.ylbl.cashpocket.utils.ToastUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class ForgetPwsAty extends BaseToolBarAty {
    @BindView(R.id.aty_forgot_phone_et)
    EditText phone;
    @BindView(R.id.aty_forgot_code_et)
    EditText code;
    @BindView(R.id.aty_forgot_pwd_et)
    EditText newPwd;
    @BindView(R.id.aty_forgot_pwd_confirm_et)
    EditText confrimPwd;
    @BindView(R.id.aty_forgot_code_btn)
    Button getCode;

    @Override
    protected int getLayoutId() {
        return R.layout.aty_forgot_pwd;
    }
    @OnClick({R.id.aty_forgot_code_btn , R.id.aty_forgot_submit_btn,R.id.aty_forgot_have_tv})
    void onClick(View view){
        switch (view.getId()){
            case R.id.aty_forgot_code_btn:
                //获取验证码
                String verPhone = phone.getText().toString().trim();
                if (TextUtils.isEmpty(verPhone)){
                    ToastUtils.toastShort(this , "请先输入手机号");
                }else {
                    Map<String ,String> params = new HashMap<>();
                    params.put("url" , Constants.USER_VERCODE);
                    params.put("mobilePhone" , verPhone);
                    newAsyncTaskExecute(Constants.HTTP_ACTION_2 ,params );
                    //计数器
                    new CountDownTimer(60000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            getCode.setEnabled(false);
                            getCode.setText(millisUntilFinished / 1000 + "");
                        }
                        @Override
                        public void onFinish() {
                            getCode.setEnabled(true);
                            getCode.setText("获取验证码");
                        }
                    }.start();
                }
                break;
            case R.id.aty_forgot_submit_btn :
                String uPhone = phone.getText().toString().trim();
                String uCode = code.getText().toString().trim();
                String uNewPwd = newPwd.getText().toString().trim();
                String uConfirmName = confrimPwd.getText().toString().trim();
                if (TextUtils.isEmpty(uPhone)){
                    ToastUtils.toastShort(this,"手机号不能为空");
                }else if (TextUtils.isEmpty(uCode)){
                    ToastUtils.toastShort(this,"验证码不能为空");
                }else if (TextUtils.isEmpty(uNewPwd)){
                    ToastUtils.toastShort(this,"密码不能为空");
                }else if (TextUtils.isEmpty(uConfirmName)){
                    ToastUtils.toastShort(this,"未确认密码");
                }else {
                    Map<String ,String> params = new HashMap();
                    params.put("url" , Constants.USER_REGISGER);
                    params.put("mobilePhone" ,uPhone );
                    params.put("verCode" ,uCode );
                    params.put("passWord" ,uNewPwd );
                    params.put("name" ,uConfirmName );
                    newAsyncTaskExecute(Constants.HTTP_ACTION_1 , params);
                }
                //确认
                break;
            case R.id.aty_forgot_have_tv :
                startActivity(new Intent(this , LoginAty.class));
                finish();
                break;

        }
    }

    @Override
    protected void doInBackgroundTask(int asyncid, Map params, Callback callback) {
        AppDbCtrl.appLogin(asyncid,params,callback );
    }
    @Override
    protected void onPostExecuteTask(int asyncid, ResultInfo resultInfo) {
        super.onPostExecuteTask(asyncid, resultInfo);
        switch (asyncid){
            case Constants.HTTP_ACTION_1:
                ToastUtils.toastShort(this , "密码修改成功");
                finish();
                break;
            case Constants.HTTP_ACTION_2:

                break;
        }
    }
}
