package com.ylbl.cashpocket.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.squareup.picasso.Picasso;
import com.ylbl.cashpocket.R;
import com.ylbl.cashpocket.base.BaseRecycleViewHolder;
import com.ylbl.cashpocket.base.BaseRecyclerAdapter;
import com.ylbl.cashpocket.base.BaseRecyclerArrayAdapter;
import com.ylbl.cashpocket.bean.PocketRecordInfo;
import com.ylbl.cashpocket.bean.SendInfo;
import com.ylbl.cashpocket.utils.DateUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PocketRecordAdapter extends BaseRecyclerArrayAdapter<PocketRecordInfo> {
    private Context context;
    private List<PocketRecordInfo> objects;
    public PocketRecordAdapter(Context context, List<PocketRecordInfo> objects) {
        super(context, objects);
        this.context = context;
        this.objects = objects;
    }

    @Override
    public void OnBindViewHolder(BaseViewHolder holder, int position) {
        super.OnBindViewHolder(holder, position);
        holder.setIsRecyclable(false);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_send , parent , false);
        return new ViewHolder(view);
    }
    class ViewHolder extends BaseRecycleViewHolder<PocketRecordInfo>{
        @BindView(R.id.item_send_day_tv)
        TextView day;
        @BindView(R.id.item_send_month_tv)
        TextView month;
        @BindView(R.id.item_details_time_tv)
        TextView date;
        @BindView(R.id.item_details_ad_icon_iv)
        ImageView picture;
        @BindView(R.id.item_details_ad_title_tv)
        TextView title;
        @BindView(R.id.item_details_ad_send_num_tv)
        TextView money;
        @BindView(R.id.item_details_ad_receive_num_tv)
        TextView moneyPool;
        @BindView(R.id.item_details_sending_tv)
        TextView state;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this , itemView);
        }

        @Override
        public void setData(PocketRecordInfo data, int position) {
            super.setData(data, position);
            day.setText(data.getOccTime().substring(8,10));
            month.setText(data.getOccTime().substring(5,7));
            if ( position >= 1 && DateUtils.isSameDay(data.getOccTime() , objects.get(position-1).getOccTime())){
                //是同一天
                day.setVisibility(View.INVISIBLE);
                month.setVisibility(View.INVISIBLE);
            }
            date.setText(data.getOccTime());
            title.setText(data.getLinkTitle());
            money.setText("发放金额：￥"+data.getPrice());
            moneyPool.setText("获取红包池：￥"+(data.getPrice()*2));
            Picasso.with(context).load(data.getImagePath()).into(picture);
            switch (data.getState()){
                case "0":
                    state.setText("关闭");
                    state.setTextColor(context.getResources().getColor(R.color.colorGray));
                    break;
                case "1":
                    state.setText("待支付");
                    state.setTextColor(context.getResources().getColor(R.color.colorRed));
                    break;
                case "2":
                    state.setText("支付完成");
                    state.setTextColor(context.getResources().getColor(R.color.colorGreen));
                    break;
            }
        }
    }
}
