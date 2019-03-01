package com.ylbl.cashpocket.base;

import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;

public abstract class BaseRecycleViewHolder <M> extends BaseViewHolder<M> {
    public BaseRecycleViewHolder(View itemView) {
        super(itemView);
    }

    public BaseRecycleViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
    }

    public void setData(M data, int position) {
    }
}
