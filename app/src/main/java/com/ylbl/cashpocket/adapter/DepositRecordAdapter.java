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
import com.ylbl.cashpocket.bean.DepositRecordInfo;
import com.ylbl.cashpocket.utils.DateUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DepositRecordAdapter extends BaseRecyclerArrayAdapter<DepositRecordInfo> {
    private Context context ;
    private List<DepositRecordInfo> datas;
    public DepositRecordAdapter(Context context, List<DepositRecordInfo> datas) {
        super(context, datas);
        this.context = context;
        this.datas = datas;
    }

    @Override
    public void OnBindViewHolder(BaseViewHolder holder, int position) {
        super.OnBindViewHolder(holder, position);
        holder.setIsRecyclable(false);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_deposit_record , parent , false);
        return new ViewHolder(view);
    }
    class ViewHolder extends BaseRecycleViewHolder<DepositRecordInfo>{
        @BindView(R.id.item_deposit_record_date_tv)
        TextView date;
        @BindView(R.id.item_deposit_name_tv)
        TextView type;
        @BindView(R.id.item_deposit_id_points_tv)
        TextView cashNum;
        @BindView(R.id.item_deposit_id_date_tv)
        TextView time;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this , itemView);
        }

        @Override
        public void setData(DepositRecordInfo data, int position) {
            super.setData(data, position);
            date.setText(data.getCreateTime().substring(0,7));
            if (position >= 1 && DateUtils.isSameDay(data.getCreateTime() , datas.get(position-1).getCreateTime())){
                //是同一天
                date.setVisibility(View.GONE);
            }
            switch (data.getLogType()){
                case 101:
                    type.setText("红包发放");
                    cashNum.setText("-"+data.getCashNum() );
                    break;
                case 102:
                    type.setText("红包领取");
                    cashNum.setText("+"+data.getCashNum() );
                    break;
                case 201:
                    type.setText("提现");
                    cashNum.setText("+"+data.getCashNum() );
                    break;
                case 301:
                    type.setText("推荐佣金");
                    cashNum.setText("+"+data.getCashNum() );
                    break;
            }
           time.setText(data.getCreateTime());
        }
    }
}
