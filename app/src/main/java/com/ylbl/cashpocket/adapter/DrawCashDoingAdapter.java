package com.ylbl.cashpocket.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.ylbl.cashpocket.R;
import com.ylbl.cashpocket.base.BaseRecycleViewHolder;
import com.ylbl.cashpocket.base.BaseRecyclerAdapter;
import com.ylbl.cashpocket.base.BaseRecyclerArrayAdapter;
import com.ylbl.cashpocket.bean.DrawCashInfo;
import com.ylbl.cashpocket.utils.DateUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DrawCashDoingAdapter extends BaseRecyclerArrayAdapter<DrawCashInfo> {
    private Context context;
    private List<DrawCashInfo> objects;
    public DrawCashDoingAdapter(Context context, List<DrawCashInfo> objects) {
        super(context, objects);
        this.context = context;
        this.objects = objects;
    }


    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_draw_cash_record_doing, parent ,false);
        return new ViewHolder(view);
    }
    class ViewHolder extends BaseRecycleViewHolder<DrawCashInfo>{
        @BindView(R.id.item_draw_cash_date_tv)
        TextView date;
        @BindView(R.id.item_draw_cash_way_tv)
        TextView road;
        @BindView(R.id.item_draw_cash_time_tv)
        TextView time;
        @BindView(R.id.item_draw_cash_money_tv)
        TextView money;
        @BindView(R.id.item_draw_cash_state_tv)
        TextView state;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this , itemView);
        }

        @Override
        public void setData(DrawCashInfo data, int position) {
            super.setData(data, position);
            date.setText(data.getCreateTime().substring(0,10));
            if (position >= 1 && DateUtils.isSameDay(data.getCreateTime() ,objects.get(position-1).getCreateTime())){
                //是同一天
                date.setVisibility(View.GONE);
            }
            time .setText(data.getCreateTime());
            road.setText((data.getWdWay()) == 1 ? "提现到支付宝 " : "提现到微信");
            money.setText("-"+data.getCashNum());
            switch (data.getState()){
                case 0:
                    state.setText("失败");
                    state.setTextColor(context.getResources().getColor(R.color.colorRed));
                    break;
                case 1:
                    state.setText("成功");
                    state.setTextColor(context.getResources().getColor(R.color.colorGreen));
                    break;
                    case 2:
                    state.setText("处理中");
                    state.setTextColor(context.getResources().getColor(R.color.colorGreen));
                    break;
            }
        }
    }
}
