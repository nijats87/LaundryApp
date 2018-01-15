package com.start.laundryapp.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.start.laundryapp.R;
import com.start.laundryapp.models.OrderModel;

public class OrdersRecyclerAdapter extends BaseRecyclerAdapter<OrderModel, OrdersRecyclerAdapter.ViewHolder> {

    public OrdersRecyclerAdapter(Activity context, OnClickListener<OrderModel> listener) {
        super(context, listener);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.rl_orders_rv, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void bindModel(ViewHolder holder, OrderModel model, int position) {
        holder.orderNumber.setText(String.valueOf(model.id));
//                Default = 0,
//                Pending = 10,
//                Received = 20,
//                OnDelivery = 30,
//                OnLaundry = 40,
//                Ready = 50,
//                Completed = 60,
//                Cancelled = 70

        switch (model.status) {
            case 10:
                holder.orderStatus.setText("Təsdiq olunmayıb");
                break;
            case 20:
                holder.orderStatus.setText("Təsdiqlənib");
                break;
            case 70:
                holder.orderStatus.setText("Ləğv olunub");
                break;
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        TextView orderNumber, orderStatus;

        ViewHolder(View view) {
            super(view);
            orderNumber = view.findViewById(R.id.orderNumber2);
            orderStatus = view.findViewById(R.id.orderStatus2);
        }
    }
}
