package com.ylbl.cashpocket.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.ylbl.cashpocket.adapter.RecyclerViewHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerViewHolder> {
    protected List<T> _data;

    public List getData() {
        return _data == null ? (_data = new ArrayList()) : _data;
    }

    @SuppressWarnings("rawtypes")
    public void setData(List data) {
        _data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (_data == null) {
            return 0;
        }
        return _data.size();
    }

    public void clear(){
        _data.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<T> collection){
        _data.addAll(collection);
        notifyDataSetChanged();
    }

    public T getItem(int position) {
        if (_data != null && _data.size() > position) {
            return _data.get(position);
        }
        return null;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerViewHolder holder = new RecyclerViewHolder(getHolderView(parent,viewType));
        return holder;
    }

    public abstract View getHolderView(ViewGroup parent, int position);
}
