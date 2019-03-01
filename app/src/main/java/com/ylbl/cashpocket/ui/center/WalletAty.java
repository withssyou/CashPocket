package com.ylbl.cashpocket.ui.center;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.ylbl.cashpocket.R;
import com.ylbl.cashpocket.base.BaseToolBarAty;
import com.ylbl.cashpocket.bean.MemberConfigInfo;
import com.ylbl.cashpocket.bean.MemberInfo;
import com.ylbl.cashpocket.bean.ResultInfo;
import com.ylbl.cashpocket.net.AppDbCtrl;
import com.ylbl.cashpocket.net.Constants;
import com.ylbl.cashpocket.utils.FastJsonUtils;
import com.ylbl.cashpocket.utils.SpUtils;
import com.ylbl.cashpocket.utils.StatusBarUtil;
import com.ylbl.cashpocket.utils.StringUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 钱包
 */
public class WalletAty extends BaseToolBarAty {
    @BindView(R.id.aty_deposit_all_money_tv)
    TextView allMoney;
    @BindView(R.id.aty_deposit_pool_money_tv)
    TextView poolMoney;
    private String money;
    private MemberInfo memberInfo;
    @Override
    protected int getLayoutId() {
        return R.layout.aty_deposit;
    }

    @Override
    protected void initViewWithBack(boolean setBack) {
        super.initViewWithBack(true);
        Intent intent = getIntent();
        money =intent.getStringExtra("allMoney");
        allMoney.setText(intent.getStringExtra("allMoney") == null ? " " : StringUtils.doubleToString(Double.parseDouble(money)));
        poolMoney.setText(intent.getStringExtra("poolMoney") == null ? " " : StringUtils.doubleToString(Double.parseDouble(intent.getStringExtra("poolMoney"))));
    }

    @Override
    protected String iniTitle() {
        return "钱包";
    }

    @OnClick({R.id.aty_deposit_money_btn, R.id.aty_deposit_record_ll, R.id.aty_deposit_details_ll,
              /*R.id.aty_deposit_team_ll,*/ R.id.aty_deposit_draw_cash_ll})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.aty_deposit_money_btn: //提现按钮
                Intent intent = new Intent(this,DrawCashAty.class);
                intent.putExtra("money" , money);
                startActivityForResult(intent , 0x18);
                break;
            case R.id.aty_deposit_record_ll: //红包记录
                startActivity(new Intent(this , PocketRecordAty.class));
                break;
            case R.id.aty_deposit_details_ll://钱包明细
                startActivity(new Intent(this , DrawCashDetailAty.class));
                break;
            case R.id.aty_deposit_draw_cash_ll://提现记录
                startActivity(new Intent(this , DrawCashRecordAty.class));
                break;

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        StatusBarUtil.setWindowStatusBarColor(this , R.color.colorBarYello);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0x20){
            Map<String ,String > params = new HashMap<>();
            params.put("url" , Constants.MEMBER_INFO);
            newAsyncTaskExecute(Constants.HTTP_ACTION_2 , params);
        }
    }
    @Override
    protected void doInBackgroundTask(int asyncid, Map params, Callback callback) {
        AppDbCtrl.getServer(asyncid ,params ,callback ,context);
    }
    @Override
    protected void onPostExecuteTask(int asyncid, ResultInfo resultInfo) {
        super.onPostExecuteTask(asyncid, resultInfo);
        switch (asyncid) {
            case Constants.HTTP_ACTION_2:
                MemberConfigInfo memberConfigInfo = FastJsonUtils.toBean(resultInfo.getData().toString() , MemberConfigInfo.class);
                if (memberConfigInfo != null) {
                    memberInfo = memberConfigInfo.getMemberInfo();
                    //缓存个人信息
                    SpUtils.saveMemberInfo(context , memberInfo);
                }
                allMoney.setText(StringUtils.doubleToString(Double.parseDouble(memberInfo.getCashNum())));
                poolMoney.setText(StringUtils.doubleToString(Double.parseDouble(memberInfo.getRpkPoolCount())));
                break;
        }
    }
}
