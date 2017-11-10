package com.start.laundryapp.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nijats87 on 11/10/2017.
 */

public abstract class BaseRecyclerAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    public Activity context;
    public List<T> data = new ArrayList<>();
    private OnClickListener<T> onClickListener;

    public interface OnClickListener<T> {
        void onClick(T model, int position);
    }

    public BaseRecyclerAdapter(Activity context, OnClickListener<T> listener) {
        this.context = context;
        this.onClickListener = listener;
    }

    @Override
    public void onBindViewHolder(VH holder, final int position) {
        final T model = data.get(position);
        bindModel(holder, model, position);

        if (onClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onClick(model, position);
                }
            });
        }
    }

    abstract public void bindModel(VH holder, T model, int position);

    @Override
    public int getItemCount() {
        return data.size();
    }
}
