package com.start.laundryapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.start.laundryapp.adapters.BaseRecyclerAdapter;
import com.start.laundryapp.adapters.OrdersRecyclerAdapter;
import com.start.laundryapp.models.ApiResponse;
import com.start.laundryapp.models.ItemsHolder;
import com.start.laundryapp.models.OrderModel;
import com.start.laundryapp.retrofit.Api;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrdersActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    SwipeRefreshLayout swipeRefreshLayout;

    ProgressBar progressBar;

    public OrdersRecyclerAdapter adapter = new OrdersRecyclerAdapter(this, new BaseRecyclerAdapter.OnClickListener<OrderModel>() {
        @Override
        public void onClick(OrderModel model, int position) {
            Intent in = new Intent(OrdersActivity.this, OrderInfoActivity.class);
            in.putExtra("orderModel", new Gson().toJson(model));
            OrdersActivity.this.startActivity(in);
        }
    });

    TextView ordersRecyclerViewTitle, noOrdersTextView, refreshFail_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        getMyOrders();

        recyclerView = findViewById(R.id.recyclerViewOrders);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        ordersRecyclerViewTitle = findViewById(R.id.ordersRecyclerViewTitle);
        noOrdersTextView = findViewById(R.id.noOrdersTextView);
        refreshFail_tv = findViewById(R.id.refresh_fail_tv);

        swipeRefreshLayout = findViewById(R.id.swipeLayout);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
                getMyOrders();
            }
        });

    }

    public void getMyOrders() {
        Call<ApiResponse<ItemsHolder<OrderModel>>> call = Api.getService().orders();
        call.enqueue(new Callback<ApiResponse<ItemsHolder<OrderModel>>>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse<ItemsHolder<OrderModel>>> call, @NonNull Response<ApiResponse<ItemsHolder<OrderModel>>> response) {
                progressBar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                refreshFail_tv.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
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
                swipeRefreshLayout.setRefreshing(false);
                progressBar.setVisibility(View.GONE);
                refreshFail_tv.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                ordersRecyclerViewTitle.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
