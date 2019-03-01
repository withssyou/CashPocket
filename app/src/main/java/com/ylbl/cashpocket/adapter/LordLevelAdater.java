package com.ylbl.cashpocket.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ylbl.cashpocket.R;
import com.ylbl.cashpocket.api.OnItemClickListener;
import com.ylbl.cashpocket.base.BaseRecyclerAdapter;
import com.ylbl.cashpocket.bean.AdsenseInfo;

public class LordLevelAdater extends BaseRecyclerAdapter<AdsenseInfo> {
    private Context context;
    private OnItemClickListener onItemClickListener;
    public LordLevelAdater(Context context , OnItemClickListener onItemClickListener) {
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public View getHolderView(ViewGroup parent, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_lord_become , parent ,false);
//        onItemClickListener.onItemClickListener(position);
        return view;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int i) {
        holder.setIsRecyclable(false);
        ((TextView)(holder.getView(R.id.item_lord_level_tv))).setText(_data.get(i).getName());
        ((TextView)(holder.getView(R.id.item_lord_money_tv))).setText(_data.get(i).getPrice());
        ((TextView)(holder.getView(R.id.item_lord_suggest_tv))).setText(_data.get(i).getExposureNum()+"次广告曝光量送"+_data.get(i).getIntegralNum()+"消费积分");
    }
}
