package com.ylbl.cashpocket.ui.redenvelops;

import android.content.Intent;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.ylbl.cashpocket.R;
import com.ylbl.cashpocket.adapter.GoodsAdapter;
import com.ylbl.cashpocket.adapter.RecordAdapter;
import com.ylbl.cashpocket.api.OnItemClickListener;
import com.ylbl.cashpocket.base.BaseToolBarAty;
import com.ylbl.cashpocket.bean.GoodsInfo;
import com.ylbl.cashpocket.bean.ResultInfo;
import com.ylbl.cashpocket.net.AppDbCtrl;
import com.ylbl.cashpocket.net.Constants;
import com.ylbl.cashpocket.utils.FastJsonUtils;
import com.ylbl.cashpocket.utils.StatusBarUtil;
import com.ylbl.cashpocket.utils.StringUtils;
import com.ylbl.cashpocket.utils.ToastUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 红包详情
 */
public class PocketDetailsAty extends BaseToolBarAty implements OnItemClickListener{
    @BindView(R.id.aty_pocket_details_icon_civ)
    CircleImageView icon;
    @BindView(R.id.aty_pocket_details_name_tv)
    TextView name;
    @BindView(R.id.aty_pocket_details_title_tv)
    TextView title;
    @BindView(R.id.aty_pocket_details_money_tv)
    TextView money;
    @BindView(R.id.aty_pocket_details_content_tv)
    TextView cotent;
    @BindView(R.id.aty_pocket_details_buy_btn)
    Button buy;
//    @BindView(R.id.bar_back)
//    Button number;
    @BindView(R.id.aty_pocket_details_goods_rv)
    RecyclerView goods;
    private GoodsAdapter goodsAdapter;
    private String link;
    private GoodsInfo datas;
    @Override
    protected int getLayoutId() {
        return R.layout.aty_pocket_details;
    }

    @Override
    protected String iniTitle() {
        return "红包详情";
    }


    @Override
    protected void initViewWithBack(boolean setBack) {
        super.initViewWithBack(true);
        back.setClickable(false);
        Intent intent = getIntent();
        int id = intent.getIntExtra("goodId" , 0);
        //添加适配器(图片)
        goods.setLayoutManager(new GridLayoutManager(this , 3));
        goodsAdapter = new GoodsAdapter(context , this);
        goods.setAdapter(goodsAdapter);

        Map<String ,String> params = new HashMap<>();
        params.put("url" , Constants.RED_ENVELOPS_DETAIL);
        params.put("id" , id+"");
        newAsyncTaskExecute(Constants.HTTP_ACTION_1 , params);
        back.setBackground(null);
    }

    @OnClick({R.id.aty_pocket_details_buy_btn ,R.id.bar_back})
    void onClick(View view){
        switch (view.getId()){
            case R.id.aty_pocket_details_buy_btn:
                    if (!TextUtils.isEmpty(link)){
                        if ((Patterns.WEB_URL.matcher(link).matches() || URLUtil.isValidUrl(link))){
                            Uri uri = Uri.parse(link);
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(intent);
                        }else {
                            ToastUtils.toastShort(this , "该红包提供链接地址有误");
                        }
                    }else {
                        ToastUtils.toastShort(this , "该用户未提供链接地址");
                    }
                break;
            case R.id.bar_back:
                if (!TextUtils.isEmpty(back.getText())){
                    ToastUtils.toastShort(context , "请稍等");
                }else {
                    finish();
                }
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        StatusBarUtil.setWindowStatusBarColor(this,R.color.colorRedTitle);
    }

    @Override
    protected void doInBackgroundTask(int asyncid, Map params, Callback callback) {
        super.doInBackgroundTask(asyncid, params, callback);
        AppDbCtrl.getServer(asyncid , params , callback ,context);
    }

    @Override
    protected void onPostExecuteTask(int asyncid, ResultInfo resultInfo) {
        super.onPostExecuteTask(asyncid, resultInfo);
        new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                back.setText(millisUntilFinished / 1000 + "");
            }
            @Override
            public void onFinish() {
                back.setText("");
                back.setBackgroundResource(R.drawable.back);
                back.setClickable(true);
            }
        }.start();

        datas = FastJsonUtils.toBean(resultInfo.getData().toString() , GoodsInfo.class);
        if (datas != null  ){
            //添加内容
            if (TextUtils.isEmpty(datas.getMemHeadImg())){
                icon.setImageResource(R.mipmap.icon_default);
            }else {
                Picasso.with(this).load(datas.getMemHeadImg()).error(R.mipmap.icon_default).into(icon);
            }
            if (!TextUtils.isEmpty(datas.getMemName().trim())){
                name.setText(datas.getMemName().trim());
            }
            if (!TextUtils.isEmpty(datas.getLinkTitle().trim())){
                title.setText(datas.getLinkTitle().trim());
            }
            if (!TextUtils.isEmpty(datas.getRedPacketMoney())){
                money.setText(StringUtils.stringToString(datas.getRedPacketMoney()));
            }
            if (!TextUtils.isEmpty(datas.getContent().trim())){
                cotent.setText(datas.getContent().trim());
            }
            if (TextUtils.isEmpty(datas.getLinkAddress())){
                buy.setVisibility(View.GONE);
            }else {
                link = datas.getLinkAddress().trim();
            }
            if (datas.getImgList() != null && datas.getImgList().size() > 0){
                goodsAdapter.setData(datas.getImgList());
            }
        }else {
            ToastUtils.toastShort(context , "红包详情加载失败");
        }
    }
    //重写back键

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (TextUtils.isEmpty(back.getText())&& keyCode == KeyEvent.KEYCODE_BACK ){
            return super.onKeyDown(keyCode, event);
        }else {
            ToastUtils.toastShort(context , "请耐心观看几秒");
            return false;
        }
    }

    @Override
    public void onItemClick(View v, int position) {
        String url = datas.getImgList().get(position);
        Intent intent = new Intent(context , ShowImageAty.class);
        intent.putExtra("img" , url);
        startActivity(intent);
    }
}
