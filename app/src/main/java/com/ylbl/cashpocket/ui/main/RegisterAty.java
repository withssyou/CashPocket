package com.ylbl.cashpocket.ui.main;

import android.content.Intent;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.ylbl.cashpocket.R;
import com.ylbl.cashpocket.base.BaseToolBarAty;
import com.ylbl.cashpocket.bean.ResultInfo;
import com.ylbl.cashpocket.bean.User;
import com.ylbl.cashpocket.net.AppDbCtrl;
import com.ylbl.cashpocket.net.Constants;
import com.ylbl.cashpocket.ui.news.RulesAty;
import com.ylbl.cashpocket.utils.SpUtils;
import com.ylbl.cashpocket.utils.ToastUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *  注册页面
 */
public class RegisterAty extends BaseToolBarAty {
    @BindView(R.id.aty_register_phone_et)
    EditText phone;
    @BindView(R.id.aty_register_code_et)
    EditText code;
    @BindView(R.id.aty_register_pwd_et)
    EditText pwd;
    @BindView(R.id.aty_register_name_et)
    EditText name;
    @BindView(R.id.aty_register_invite_et)
    EditText invite;
    @BindView(R.id.aty_register_code_btn)
    Button getCode;
    @BindView(R.id.aty_register_serve_cb)
    CheckBox checkBox;

    private String uPhone , uPwd , uName;
    @Override
    protected int getLayoutId() {
        return R.layout.aty_register;
    }

    @Override
    protected String iniTitle() {
        return "注 册";
    }
    @OnClick({R.id.aty_register_code_btn ,R.id.aty_register_register_btn ,R.id.aty_register_serve_tv })
    void click(View view){
        switch (view.getId()){
            case R.id.aty_register_code_btn:
                String verPhone = phone.getText().toString().trim();
                if (TextUtils.isEmpty(verPhone)){
                    ToastUtils.toastShort(this , "请先输入手机号");
                }else {
                    Map<String ,String> params = new HashMap<>();
                           params.put("url" , Constants.USER_VERCODE);
                           params.put("mobilePhone" , verPhone);
                    newAsyncTaskExecute(Constants.HTTP_ACTION_2 ,params );
                    //计数器
                   CountDownTimer countDownTimer =  new CountDownTimer(60000, 1000) {
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
            case R.id.aty_register_register_btn:
                if (checkBox.isChecked()){
                    uPhone = phone.getText().toString().trim();
                    String uCode = code.getText().toString().trim();
                    uPwd = pwd.getText().toString().trim();
                    uName = name.getText().toString().trim();
                    String uInvite = invite.getText().toString().trim();
                    if (TextUtils.isEmpty(uPhone)){
                        ToastUtils.toastShort(this,"手机号不能为空");
                    }else if (TextUtils.isEmpty(uCode)){
                        ToastUtils.toastShort(this,"验证码不能为空");
                    }else if (TextUtils.isEmpty(uPwd)){
                        ToastUtils.toastShort(this,"密码不能为空");
                    }else if (TextUtils.isEmpty(uName)){
                        ToastUtils.toastShort(this,"昵称不能为空");
                    }else if (TextUtils.isEmpty(uInvite)){
                        ToastUtils.toastShort(this,"邀请码不能为空");
                    }else {
                        Map<String ,String> params = new HashMap();
                        params.put("url" , Constants.USER_REGISGER);
                        params.put("mobilePhone" ,uPhone );
                        params.put("verCode" ,uCode );
                        params.put("passWord" ,uPwd );
                        params.put("name" ,uName );
                        params.put("parentMobilePhone" ,uInvite );
                        newAsyncTaskExecute(Constants.HTTP_ACTION_1 , params);
                    }
                    break;
                }else {
                    ToastUtils.toastShort(context , "请先阅读协议无异议请勾选");
                }
            case R.id.aty_register_serve_tv:
                Intent intent = new Intent(context , RulesAty.class);
                intent.putExtra("url" , Constants.URL_SERVE);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void doInBackgroundTask(int asyncid, Map params, Callback callback) {
//        super.doInBackgroundTask(asyncid, params, callback);
        AppDbCtrl.appLogin(asyncid,params,callback );
    }

    @Override
    protected void onPostExecuteTask(int asyncid, ResultInfo resultInfo) {
        super.onPostExecuteTask(asyncid, resultInfo);
        switch (asyncid){
            case Constants.HTTP_ACTION_1:
                //解析结果
                if (!TextUtils.isEmpty(resultInfo.getToken())){
                    User user = new User();
                    user.setPwd(uPwd);
                    user.setPhone(uName);
                    user.setName(uName);
                    user.setToken(resultInfo.getToken());
                    //保存登录状态
                    SpUtils.saveLogin(this ,user);
                    setResult(0x14 , null);
                    if (!isExsitMianActivity(MainAty.class)){
                        startActivity(new Intent(context , MainAty.class));
                    }
                    finish();
                }else {
                    ToastUtils.toastShort(this , "登录失败");
                }
                break;
            case Constants.HTTP_ACTION_2:
                    break;
        }
    }
}
