package com.ylbl.cashpocket.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.squareup.picasso.Picasso;
import com.ylbl.cashpocket.R;
import com.ylbl.cashpocket.base.BaseRecycleViewHolder;
import com.ylbl.cashpocket.base.BaseRecyclerArrayAdapter;
import com.ylbl.cashpocket.bean.NewsInfo;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NewsAdapter extends BaseRecyclerArrayAdapter<NewsInfo> {
    private Context context;
    private OnClickListener onClickListener;

    public NewsAdapter(Context context, List<NewsInfo> objects, OnClickListener onClickListener) {
        super(context, objects, onClickListener);
        this.context  = context;
        this.onClickListener = onClickListener;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_frag_news , parent,false);
        return new ViewHolder(view);
    }
    class ViewHolder extends BaseRecycleViewHolder<NewsInfo>{
        @BindView(R.id.item_frag_news_title_tv)
        TextView title;
        @BindView(R.id.item_frag_news_date_tv)
        TextView date;
        @BindView(R.id.item_frag_news_icon_iv)
        ImageView picture;
        @BindView(R.id.item_frag_news_go_ll)
        LinearLayout ll;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this , itemView);
        }

        @Override
        public void setData(NewsInfo data, int position) {
            super.setData(data, position);
            title.setText(data.getTitle());
            date.setText(data.getCreateTime());
            Picasso.with(context).load(data.getTitleImage()).into(picture);
//            setOnClickListener(onClickListener);
            ll.setOnClickListener(getOnClickListener(position));
        }
    }
}
