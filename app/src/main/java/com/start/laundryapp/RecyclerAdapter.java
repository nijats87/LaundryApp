package com.start.laundryapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by nijats87 on 10/2/2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecycleViewHolder> {
    @Override
    public RecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout_rview, parent, false);

        RecycleViewHolder recycleViewHolder = new RecycleViewHolder(view);
        return recycleViewHolder;
    }

    @Override
    public void onBindViewHolder(RecycleViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class RecycleViewHolder extends RecyclerView.ViewHolder {

        TextView tw, tw1;

        public RecycleViewHolder(View view){
            super(view);
            tw = (TextView)view.findViewById(R.id.row_layout_tw);
            tw1 = (TextView)view.findViewById(R.id.row_layout_tw1);


        }
    }
}
