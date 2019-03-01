package com.ylbl.cashpocket.base;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CompoundButton;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.math.BigDecimal;
import java.util.List;

public abstract class BaseRecyclerArrayAdapter<T> extends RecyclerArrayAdapter<T> {
    protected OnClickListener onClickListener;
    protected OnRecyclerArrayCheckListener onRecyclerArrayCheckListener;
    protected OnGoodsCheckedChangeListener onGoodsCheckedChangeListener;
    protected OnAllCheckedBoxNeedChangeListener onAllCheckedBoxNeedChangeListener;

    public BaseRecyclerArrayAdapter(Context context) {
        super(context);
    }

    public BaseRecyclerArrayAdapter(Context context, T[] objects) {
        super(context, objects);
    }

    public BaseRecyclerArrayAdapter(Context context, List<T> objects) {
        super(context, objects);
    }

    public BaseRecyclerArrayAdapter(Context context, List<T> objects, OnClickListener onClickListener) {
        super(context, objects);
        this.onClickListener = onClickListener;
    }

    public BaseRecyclerArrayAdapter(Context context, List<T> objects, OnClickListener onClickListener,OnRecyclerArrayCheckListener onRecyclerArrayCheckListener) {
        super(context, objects);
        this.onClickListener = onClickListener;
        this.onRecyclerArrayCheckListener = onRecyclerArrayCheckListener;
    }

    public void OnBindViewHolder(BaseViewHolder holder, final int position) {
        if (holder instanceof BaseRecycleViewHolder) {
            ((BaseRecycleViewHolder) holder).setData(getItem(position), position);
        } else {
            holder.setData(getItem(position));
        }
    }

    public interface OnClickListener {
        void onRecyclerArrayClick(View view, Object data, int position);
    }

    public interface OnRecyclerArrayCheckListener {
        void OnRecyclerArrayCheckClick(boolean checked, Object data, int position);
    }

    public CompoundButton.OnCheckedChangeListener getOnRecyclerArrayCheckListener(final int position) {
        return new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (onRecyclerArrayCheckListener != null) {
                    onRecyclerArrayCheckListener.OnRecyclerArrayCheckClick(isChecked, mObjects.get(position), position);
                }
            }
        };
    }

    public void setOnRecyclerArrayCheckListener(OnRecyclerArrayCheckListener onRecyclerArrayCheckListener) {
        this.onRecyclerArrayCheckListener = onRecyclerArrayCheckListener;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public View.OnClickListener getOnClickListener(final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(@Nullable View v) {
                if (onClickListener != null && v != null) {
                    if (mObjects.size() == position) {
                        onClickListener.onRecyclerArrayClick(v, null, position);
                    } else {
                        onClickListener.onRecyclerArrayClick(v, mObjects.get(position), position);
                    }
                }
            }
        };
    }

    public interface OnGoodsCheckedChangeListener {
        void onGoodsCheckedChange(BigDecimal[] goodsInfo);
    }

    public interface OnAllCheckedBoxNeedChangeListener {
        void onCheckedBoxNeedChange(boolean allParentIsChecked);
    }
    public void setOnGoodsCheckedChangeListener(OnGoodsCheckedChangeListener onGoodsCheckedChangeListener) {
        this.onGoodsCheckedChangeListener = onGoodsCheckedChangeListener;
    }

    public void setOnAllCheckedBoxNeedChangeListener(OnAllCheckedBoxNeedChangeListener onAllCheckedBoxNeedChangeListener) {
        this.onAllCheckedBoxNeedChangeListener = onAllCheckedBoxNeedChangeListener;
    }
}
