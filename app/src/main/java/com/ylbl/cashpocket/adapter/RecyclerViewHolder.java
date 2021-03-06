package com.ylbl.cashpocket.adapter;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.SparseArray;
import android.view.View;
import android.widget.Adapter;

public class RecyclerViewHolder extends ViewHolder {
    private SparseArray<View> views;
    private View convertView;

    public RecyclerViewHolder(View convertView) {
        super(convertView);
        this.views = new SparseArray();
        this.convertView = convertView;
    }

    public <T extends View> T getView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = convertView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    public View getConvertView() {
        return convertView;
    }
}
