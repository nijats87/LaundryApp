package com.start.laundryapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.start.laundryapp.adapters.BaseRecyclerAdapter;
import com.start.laundryapp.adapters.OrdersRecyclerAdapter;
import com.start.laundryapp.models.ApiResponse;
import com.start.laundryapp.models.ItemsHolder;
import com.start.laundryapp.models.OrderModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrdersActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    public OrdersRecyclerAdapter adapter = new OrdersRecyclerAdapter(this, new BaseRecyclerAdapter.OnClickListener<OrderModel>() {
        @Override
        public void onClick(OrderModel model, int position) {
            Intent in = new Intent(OrdersActivity.this, OrderInfoActivity.class);
            in.putExtra("orderModel", new Gson().toJson(model));
            OrdersActivity.this.startActivity(in);
        }
    });

    TextView ordersRecyclerViewTitle, noOrdersTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);


        recyclerView = findViewById(R.id.recyclerViewOrders);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        ordersRecyclerViewTitle = findViewById(R.id.ordersRecyclerViewTitle);
        noOrdersTextView = findViewById(R.id.noOrdersTextView);

        getMyOrders();

    }

    public void getMyOrders() {
        Call<ApiResponse<ItemsHolder<OrderModel>>> call = Api.getService().orders();
        call.enqueue(new Callback<ApiResponse<ItemsHolder<OrderModel>>>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse<ItemsHolder<OrderModel>>> call, @NonNull Response<ApiResponse<ItemsHolder<OrderModel>>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<ItemsHolder<OrderModel>> body = response.body();
                    if (body.result.items.size() != 0) {
                        ordersRecyclerViewTitle.setVisibility(View.VISIBLE);
                        adapter.data = body.result.items;
                        adapter.notifyDataSetChanged();
                        return;

                    } else {
                        noOrdersTextView.setVisibility(View.VISIBLE);
                        return;
                    }
                }
                Toast.makeText(OrdersActivity.this, "Request was not succesful. Code: " + response.code(), Toast.LENGTH_SHORT).show();
            }

            public void onFailure(@NonNull Call<ApiResponse<ItemsHolder<OrderModel>>> call, Throwable t) {
                Log.e("dsdsd", "onFailure: ", t);
            }
        });
    }

}
