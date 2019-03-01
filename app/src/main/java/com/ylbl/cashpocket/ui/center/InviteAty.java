package com.ylbl.cashpocket.ui.center;

import android.content.ClipboardManager;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ylbl.cashpocket.R;
import com.ylbl.cashpocket.base.BaseHttpAty;
import com.ylbl.cashpocket.bean.ResultInfo;
import com.ylbl.cashpocket.net.AppDbCtrl;
import com.ylbl.cashpocket.net.Constants;
import com.ylbl.cashpocket.utils.StatusBarUtil;
import com.ylbl.cashpocket.utils.StringUtils;
import com.ylbl.cashpocket.utils.ToastUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 邀请
 */
public class InviteAty extends BaseHttpAty{
    @BindView(R.id.aty_invite_code_iv)
    ImageView codeIv;
    private String qrCode;
    private String extUrl;

    @Override
    protected int getLayoutId() {
        return R.layout.aty_invite;
    }

    @Override
    protected String iniTitle() {
        return "";
    }

    @Override
    protected void initViewWithBack(boolean setBack) {
        super.initViewWithBack(setBack);
        Map<String ,String> params = new HashMap<>();
        params.put("url" , Constants.EXTENSION);
        newAsyncTaskExecute(Constants.HTTP_ACTION_1 , params);
    }

    @OnClick({R.id.aty_invite_back_iv,R.id.aty_invite_help_iv,R.id.aty_invite_copy_btn})
    void onClick(View view){
        switch (view.getId()){
            case R.id.aty_invite_back_iv:
                finish();
                break;
            case R.id.aty_invite_help_iv:

                break;
//            case R.id.aty_invite_share_btn:
//                break;
            case R.id.aty_invite_copy_btn:
                if (TextUtils.isEmpty(extUrl)){
                    ToastUtils.toastShort(context , "获取邀请二维码失败");
                }else {
                    ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    // 将文本内容放到系统剪贴板里。
                    cm.setText(extUrl);
                    ToastUtils.toastShort(context , "复制邀请链接成功");
                }
                break;
        }
    }

    @Override
    protected void doInBackgroundTask(int asyncid, Map params, Callback callback) {
        AppDbCtrl.getServer(asyncid , params ,callback ,context);
    }

    @Override
    protected void onPostExecuteTask(int asyncid, ResultInfo resultInfo) {
        super.onPostExecuteTask(asyncid, resultInfo);
        qrCode = resultInfo.getQrCode();
        extUrl = resultInfo.getExtUrl();
        if (TextUtils.isEmpty(qrCode)){
            codeIv.setImageResource(R.mipmap.ic_launcher);
        }else {
            codeIv.setImageBitmap(StringUtils.stringToBitmap(qrCode));
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        StatusBarUtil.setWindowStatusBarColor(this , R.color.colorRedBar);
    }
}
