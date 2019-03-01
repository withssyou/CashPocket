package com.ylbl.cashpocket.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.ylbl.cashpocket.R;
import com.ylbl.cashpocket.api.OnItemClickListener;
import com.ylbl.cashpocket.base.BaseRecyclerAdapter;
import com.ylbl.cashpocket.bean.GoodsInfo;

public class GoodsAdapter extends BaseRecyclerAdapter<String> {
    private Context context;
    private OnItemClickListener onItemClickListener;
    public GoodsAdapter(Context context , OnItemClickListener onItemClickListener) {
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public View getHolderView(ViewGroup parent, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_goods,parent,false);
        return view;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder recyclerViewHolder, final int i) {
        final ImageView imageView = recyclerViewHolder.getView(R.id.item_goods_photo_iv);
        Picasso.with(context).load(_data.get(i)).into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(v , i);
            }
        });

    }
}
