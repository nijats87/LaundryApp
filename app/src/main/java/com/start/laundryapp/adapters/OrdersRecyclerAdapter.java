package com.start.laundryapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.start.laundryapp.MakeOrder;
import com.start.laundryapp.OrdersActivity;
import com.start.laundryapp.R;
import com.start.laundryapp.models.OrderModel;
import com.start.laundryapp.models.OrderTypeModel;
import com.start.laundryapp.models.TerminalPointsModel;

import java.util.ArrayList;
import java.util.List;

public class OrdersRecyclerAdapter extends RecyclerView.Adapter<OrdersRecyclerAdapter.ViewHolder> {

    public Activity context;

    public List<OrderModel> data;
    public List<OrderTypeModel> orderTypeModels = OrdersActivity.orderTypes;
    public List<TerminalPointsModel> terminalPointsModels = OrdersActivity.terminalPoints;


    public OrdersRecyclerAdapter(Activity context, List<OrderModel> data) {
        this.context = context;
        this.data = data;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.rl_orders_rv, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String orderType = "";
        String terminalPoint = "";

        OrderModel orderData = data.get(position);

        for (OrderTypeModel item : orderTypeModels) {
            if (item.getId() == orderData.orderTypeId) {
                orderType = item.getNameAz();
            }
        }
        for (TerminalPointsModel item : terminalPointsModels) {
            if (item.getId() == orderData.terminalPointId) {
                terminalPoint = item.getName();
            }
        }


        holder.orderNumber.setText(String.valueOf(orderData.id));
        holder.clothesCount.setText(String.valueOf(orderData.numberOfClothes));
        holder.orderType.setText(orderType);
        holder.terminalPoint.setText(terminalPoint);
        holder.orderDate.setText(String.valueOf(orderData.orderTypeId));
        switch (orderData.status) {
            case 10:
                holder.orderStatus.setText("Pending");
                break;
            case 70:
                holder.orderStatus.setText("Cancelled");
                break;
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView orderNumber, clothesCount, orderType, terminalPoint, orderStatus, orderDate;

        public ViewHolder(View view) {
            super(view);
            orderNumber = view.findViewById(R.id.orderNumber2);
            clothesCount = view.findViewById(R.id.clothesCount2);
            orderType = view.findViewById(R.id.orderType2);
            terminalPoint = view.findViewById(R.id.terminalPoint2);
            orderStatus = view.findViewById(R.id.orderStatus2);
            orderDate = view.findViewById(R.id.orderDate2);
        }
    }
}
