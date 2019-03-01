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
import com.ylbl.cashpocket.bean.TeamLevel;

public class TeamLevelAdapter extends BaseRecyclerAdapter<TeamLevel> {
    private Context context;
    private OnItemClickListener listener;
    public TeamLevelAdapter(Context context , OnItemClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public View getHolderView(ViewGroup parent, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_team_level , parent ,false);
        return view;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, final int i) {
        holder.getView(R.id.item_team_go_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                listener.onItemClickListener(i);
            }
        });
        ((TextView)holder.getView(R.id.item_team_num_tv)).setText(_data.get(i).getNum());
        ((TextView)holder.getView(R.id.item_team_level_tv)).setText(_data.get(i)+"级推荐");
    }
}
