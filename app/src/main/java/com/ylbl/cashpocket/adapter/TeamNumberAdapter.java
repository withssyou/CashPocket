package com.ylbl.cashpocket.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
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
import com.ylbl.cashpocket.bean.Teamer;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class TeamNumberAdapter extends BaseRecyclerArrayAdapter<Teamer> {
    private Context context;

    public TeamNumberAdapter(Context context, List<Teamer> objects) {
        super(context, objects);
        this.context = context;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_team_number,parent ,false);
        return new ViewHolder(view);
    }

    class ViewHolder extends BaseRecycleViewHolder<Teamer>{
        @BindView(R.id.item_team_name_tv)
        TextView name;
        @BindView(R.id.item_team_phone_tv)
        TextView phone;
        @BindView(R.id.item_team_date_tv)
        TextView date;
        @BindView(R.id.item_team_icon_civ)
        CircleImageView icon;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this , itemView);
        }

        @Override
        public void setData(Teamer data, int position) {
            super.setData(data, position);
            name.setText(data.getName());
            phone.setText(data.getMobilePhone());
            date.setText(data.getRegisterTime().substring(0 , 9));
            if (TextUtils.isEmpty(data.getHeadImg())){
                icon.setImageResource(R.mipmap.icon_default);
            }else {
                Picasso.with(context).load(data.getHeadImg()).into(icon);
            }
        }
    }
}
