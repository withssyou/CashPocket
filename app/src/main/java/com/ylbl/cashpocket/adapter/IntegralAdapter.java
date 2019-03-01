package com.ylbl.cashpocket.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ylbl.cashpocket.R;
import com.ylbl.cashpocket.base.BaseRecyclerAdapter;
import com.ylbl.cashpocket.bean.IntegralInfo;

public class IntegralAdapter extends BaseRecyclerAdapter<IntegralInfo>{
    private Context context ;

    public IntegralAdapter(Context context) {
        this.context = context;
    }

    @Override
    public View getHolderView(ViewGroup parent, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_integral_log , parent ,false);
        return view;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int i) {
        ((TextView)(holder.getView(R.id.item_bonus_id_tv))).setText(_data.get(i).getLogType());
        ((TextView)(holder.getView(R.id.item_bonus_id_date_tv))).setText(_data.get(i).getCreateTime());
        ((TextView)(holder.getView(R.id.item_bonus_id_points_tv))).setText(_data.get(i).getIntegralNum());
    }
}
