package com.ylbl.cashpocket.ui.main;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ylbl.cashpocket.R;
import com.ylbl.cashpocket.base.BaseToolBarAty;
import com.ylbl.cashpocket.bean.ResultInfo;
import com.ylbl.cashpocket.bean.User;
import com.ylbl.cashpocket.net.AppDbCtrl;
import com.ylbl.cashpocket.net.Constants;
import com.ylbl.cashpocket.utils.SpUtils;
import com.ylbl.cashpocket.utils.StatusBarUtil;
import com.ylbl.cashpocket.utils.ToastUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginAty extends BaseToolBarAty {
    @BindView(R.id.aty_login_name_et)
    EditText name;
    @BindView(R.id.aty_login_pwd_et)
    EditText pwd;
    @BindView(R.id.aty_login_forgot_tv)
    TextView forgot;
    private String uName;
    private String uPwd;
    @Override
    protected int getLayoutId() {
        return R.layout.aty_login;
    }

    @Override
    protected String iniTitle() {
        return "登录";
    }

    @Override
    protected void initViewWithBack(boolean setBack) {
        super.initViewWithBack(setBack);
        StatusBarUtil.setWindowStatusBarColor(this , R.color.white);
    }

    @OnClick({R.id.aty_login_login_btn ,R.id.aty_login_register_btn ,R.id.aty_login_forgot_tv})
    void onClick(View view){
      switch (view.getId()){
          case R.id.aty_login_login_btn:
              uName = name.getText().toString().trim();
              uPwd = pwd.getText().toString().trim();
              if (TextUtils.isEmpty(uName)){
                  ToastUtils.toastShort(this,"用户名不能为空");
              }else if(TextUtils.isEmpty(uPwd)){
                  ToastUtils.toastShort(this,"密码不能为空");
              }else {
                  Map params = new HashMap();
                  params.put("url" , Constants.USER_LOGIN);
                  params.put("mobilePhone" , uName);
                  params.put("passWord" , uPwd);
//                  ToastUtils.toastShort(this,"登录成功");
            newAsyncTaskExecute(Constants.HTTP_ACTION_1 ,params);
              }
              break;
          case  R.id.aty_login_register_btn:
              startActivityForResult(new Intent(this , RegisterAty.class) ,0x12);
              finish();
              break;
          case R.id.aty_login_forgot_tv:
              startActivity(new Intent(this ,ForgetPwsAty.class));
              break;
      }
   }

    @Override
    protected void doInBackgroundTask(int asyncid, Map params, Callback callback) {
//        super.doInBackgroundTask(asyncid, params, callback);
        AppDbCtrl.appLogin(asyncid , params ,callback);
    }

    @Override
    protected void onPostExecuteTask(int asyncid, ResultInfo resultInfo) {
        super.onPostExecuteTask(asyncid, resultInfo);
        switch (asyncid){
            case Constants.HTTP_ACTION_1: //登录
                //解析结果
                if (!TextUtils.isEmpty(resultInfo.getToken())){
                    User user = new User();
                    user.setPwd(uPwd);
                    user.setPhone(uName);
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

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0x14){
            setResult(0x14);
        }
    }
}
