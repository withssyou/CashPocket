package com.ylbl.cashpocket.fmg;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.ylbl.cashpocket.R;
import com.ylbl.cashpocket.base.BaseHttpFmg;
import com.ylbl.cashpocket.bean.ConfigInfo;
import com.ylbl.cashpocket.bean.MemberConfigInfo;
import com.ylbl.cashpocket.bean.MemberInfo;
import com.ylbl.cashpocket.bean.ResultInfo;
import com.ylbl.cashpocket.net.AppDbCtrl;
import com.ylbl.cashpocket.net.Constants;
import com.ylbl.cashpocket.ui.news.HelpCommentAty;
import com.ylbl.cashpocket.ui.news.RulesAty;
import com.ylbl.cashpocket.ui.center.TeamSecondAty;
import com.ylbl.cashpocket.ui.center.WalletAty;
import com.ylbl.cashpocket.ui.center.InviteAty;
import com.ylbl.cashpocket.ui.center.ModifyInfoAty;
import com.ylbl.cashpocket.ui.center.PayBindAty;
import com.ylbl.cashpocket.ui.main.SettingAty;
import com.ylbl.cashpocket.utils.FastJsonUtils;
import com.ylbl.cashpocket.utils.ImageCacheUtils;
import com.ylbl.cashpocket.utils.SpUtils;
import com.ylbl.cashpocket.utils.StringUtils;
import com.ylbl.cashpocket.utils.VersionUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class MyFmg extends BaseHttpFmg {
    @BindView(R.id.fmg_my_title_civ)
    CircleImageView icon;
    @BindView(R.id.fmg_my_name_tv)
    TextView name;
    @BindView(R.id.fmg_my_id_icon_iv)
    ImageView help; //客服
    @BindView(R.id.fmg_my_all_money_tv)
    TextView allMoney; //金额
    @BindView(R.id.fmg_my_yesterday_money_tv)
    TextView yesInCome; //昨日收入

    private MemberInfo memberInfo;
    private ConfigInfo configInfo;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    protected String iniTitle() {
        return "我的";
    }

    @Override
    protected void initViewWithBack(boolean setBack) {
        super.initViewWithBack(setBack);
    }

    @OnClick({R.id.fmg_my_deposit_btn, R.id.fgm_my_pocket_iv, R.id.my_invite,
            R.id.my_team,R.id.my_setting, R.id.fmg_my_id_icon_iv, R.id.fragment_my_modify_info_iv,
            R.id.my_pay_bind })
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.fmg_my_deposit_btn: //提现
                Intent wallet = new Intent(context, WalletAty.class);
                if (memberInfo != null) {
                    wallet.putExtra("allMoney", memberInfo.getCashNum()+"");
                    wallet.putExtra("poolMoney", memberInfo.getRpkPoolCount()+"");
                }
                startActivity(wallet);
                break;
            case R.id.fgm_my_pocket_iv:

                break;
            case R.id.my_invite: //邀请
                startActivity(new Intent(context, InviteAty.class));
                break;
            case R.id.my_team: //我的团队
                startActivity(new Intent(context, TeamSecondAty.class));
                break;

            case R.id.my_setting: //设置
                startActivity(new Intent(context, SettingAty.class));
                break;
            case R.id.fragment_my_modify_info_iv://修改个人信息
                Intent intent = new Intent(context , ModifyInfoAty.class);
                if (memberInfo != null){
                    intent.putExtra("phone" ,memberInfo.getMobilePhone());
                    intent.putExtra("headImg" ,memberInfo.getHeadImg());
                    intent.putExtra("name" ,memberInfo.getName());
                    intent.putExtra("gender" ,memberInfo.getGender());
                    intent.putExtra("birthday" ,memberInfo.getBirthday());
                    if (!TextUtils.isEmpty(memberInfo.getProvince()) && !TextUtils.isEmpty(memberInfo.getCity())) {
                        intent.putExtra("address", memberInfo.getProvince() + memberInfo.getCity());
                    }
                    intent.putExtra("jobs" ,memberInfo.getIndustry());
                }
                startActivity(intent);
                break;
            case R.id.fmg_my_id_icon_iv: //客服
                startActivity(new Intent(context , HelpCommentAty.class));
                break;
            case R.id.my_pay_bind:   //支付绑定
                startActivity(new Intent(context , PayBindAty.class));
                break;
//            case R.id.fmg_my_level_up_tv:   //如何升级
//                startActivity(new Intent(context , RulesAty.class));
//                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Map<String ,String > params = new HashMap<>();
        params.put("url" , Constants.MEMBER_INFO);
        newAsyncTaskExecute(Constants.HTTP_ACTION_2 , params);
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
                    configInfo = memberConfigInfo.getSysconfig();
                    //缓存个人信息
                    SpUtils.saveMemberInfo(context , memberInfo);
                    SpUtils.saveConfigInfo(context ,configInfo);
                }
                //信息展示
                if (TextUtils.isEmpty(memberInfo.getHeadImg())){
                    icon.setImageResource(R.mipmap.icon_default);
                }else {
                    Picasso.with(context).load(memberInfo.getHeadImg()).error(R.mipmap.icon_default).into(icon);
                }
                name.setText(memberInfo.getName());
                Drawable drawableRight;
                switch (memberInfo.getLevelCode()){
                    case 1:
                        //普通用户
                        drawableRight = getResources().getDrawable(R.mipmap.vip_nomal);
                        name.setCompoundDrawablesWithIntrinsicBounds(drawableRight,null, null, null);
                        name.setCompoundDrawablePadding(4);
                        break;
                    case 2:
                        drawableRight = getResources().getDrawable(R.mipmap.vip);
                        name.setCompoundDrawablesWithIntrinsicBounds(drawableRight,null, null, null);
                        name.setCompoundDrawablePadding(4);
                        break;
                }
                allMoney.setText(StringUtils.doubleToString(Double.parseDouble(memberInfo.getCashNum())));
                if (TextUtils.isEmpty(memberInfo.getProfit())){
                    yesInCome.setText("昨日抢到红包的数量是"+0.00);
                }else {
                    yesInCome.setText("昨日抢到红包的数量是"+StringUtils.doubleToString(Double.parseDouble(memberInfo.getProfit())));
                }
                break;
        }
    }
}
