package com.ylbl.cashpocket.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.ylbl.cashpocket.R;
import com.ylbl.cashpocket.base.BaseRecyclerAdapter;
import com.ylbl.cashpocket.bean.AdLordInfo;

public class AdLordAdapter extends BaseRecyclerAdapter<AdLordInfo>{
    private Context context;

    public AdLordAdapter(Context context) {
        this.context = context;
    }

    @Override
    public View getHolderView(ViewGroup parent, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_ad_lord , parent ,false);
        return view;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int i) {
        ((TextView)holder.getView(R.id.item_id_lord_date_tv)).setText(_data.get(i).getCreateTime());
//        ((TextView)holder.getView(R.id.item_id_lord_rest_tv)).setText(_data.get(i).get);
        ((TextView)holder.getView(R.id.item_id_lord_money_tv)).setText(_data.get(i).getPrice());
        ((TextView)holder.getView(R.id.item_id_lord_times_tv)).setText(_data.get(i).getExposureNum()+"次曝光次数");
        ((TextView)holder.getView(R.id.item_id_lord_jifen_tv)).setText(_data.get(i).getIntetralNum()+"积分");
        Picasso.with(context).load(_data.get(i).getIcon()).into((ImageView)holder.getView(R.id.item_ad_lord_icon_iv));
        if (_data.get(i).getIsPublish() == 0+""){
            holder.getView(R.id.item_id_lord_doing_btn).setVisibility(View.VISIBLE);
        }else {
            holder.getView(R.id.item_id_lord_done_btn).setVisibility(View.VISIBLE);
        }
    }
}
