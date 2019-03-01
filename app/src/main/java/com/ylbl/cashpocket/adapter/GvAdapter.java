package com.ylbl.cashpocket.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.ylbl.cashpocket.R;

import java.util.List;

public class GvAdapter extends BaseAdapter {
    private Context context;
    private int mMaxPosition;//根据这个list.size+1 来进行判断
    private List<Bitmap> list;

    public GvAdapter(Context context, List<Bitmap> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        mMaxPosition = list.size() + 1;
        return mMaxPosition;
    }

    //根据情况可以设置是否需要。。
    public int getMaxPosition() {
        return mMaxPosition;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View v, ViewGroup parent) {
        ViewHolder vh = null;
        if (v == null) {
            vh = new ViewHolder();
            v = LayoutInflater.from(context).inflate(R.layout.item_gd, parent, false);
            vh.img =  v.findViewById(R.id.img);
            vh.demimg = v.findViewById(R.id.delimg);
            v.setTag(vh);
        } else {
            vh = (ViewHolder) v.getTag();
        }
        if (position == mMaxPosition - 1) { //说明要显示
            Picasso.with(context).load(R.mipmap.image_add).into(vh.img);
            vh.img.setVisibility(View.VISIBLE);
            vh.demimg.setVisibility(View.GONE);
            if (position == 9 && mMaxPosition == 10) {//设置最大6个。那么达到最大，就隐藏。
//                vh.img.setImageResource(R.drawable.id_photo);
                vh.img.setVisibility(View.GONE);
            }
        } else {//设置图片。
            vh.demimg.setVisibility(View.VISIBLE);
//            Picasso.with(context).load(list.get(position)).into(vh.img);//设置
            vh.img.setImageBitmap(list.get(position));
        }
        //删除
        vh.demimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove(position);
                notifyDataSetChanged();
            }
        });
        return v;
    }
}
     class ViewHolder{
        public ImageView img,demimg;
    }

