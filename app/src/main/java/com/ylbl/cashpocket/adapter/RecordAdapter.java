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

import com.ylbl.cashpocket.bean.RecordsInfo;

public class RecordAdapter extends BaseRecyclerAdapter<RecordsInfo> {
    private Context context ;

    public RecordAdapter(Context context) {
        this.context = context;
    }

    @Override
    public View getHolderView(ViewGroup parent, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_records,parent,false);
        return view;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int i) {
        ImageView view = holder.getView(R.id.item_records_icon_civ);
        Picasso.with(context).load(_data.get(i).getMemHeadImg()).into(view);
        ((TextView)holder.getView(R.id.item_records_name_tv)).setText(_data.get(i).getMemName());
        ((TextView)holder.getView(R.id.item_records_date_tv)).setText(_data.get(i).getRecTime());
        ((TextView)holder.getView(R.id.item_records_money_tv)).setText(_data.get(i).getMoney());
    }
}
