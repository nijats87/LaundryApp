package com.start.laundryapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.start.laundryapp.adapters.OrdersRecyclerAdapter;
import com.start.laundryapp.models.ApiResponse;
import com.start.laundryapp.models.ItemsHolder;
import com.start.laundryapp.models.OrderModel;
import com.start.laundryapp.models.OrderTypeModel;
import com.start.laundryapp.models.TerminalPointsModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrdersActivity extends AppCompatActivity {

    public static String TAG = OrdersActivity.class.getSimpleName();
    public ArrayList<OrderModel> data = new ArrayList<>();
    public static ArrayList<OrderTypeModel> orderTypes = new ArrayList<>();
    public static ArrayList<TerminalPointsModel> terminalPoints = new ArrayList<>();
    RecyclerView recyclerView;

    public OrdersRecyclerAdapter adapter = new OrdersRecyclerAdapter(this, data);

    TextView ordersRecyclerViewTitle, noOrdersTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        getMyOrders();
        getOrderTypesData();
        getTerminalPointsData();

        recyclerView = findViewById(R.id.recyclerViewOrders);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        ordersRecyclerViewTitle = findViewById(R.id.ordersRecyclerViewTitle);
        noOrdersTextView = findViewById(R.id.noOrdersTextView);
    }


    public void getMyOrders() {
        Call<ApiResponse<ItemsHolder<OrderModel>>> call = Api.getService().orders();
        call.enqueue(new Callback<ApiResponse<ItemsHolder<OrderModel>>>() {
            @Override
            public void onResponse(Call<ApiResponse<ItemsHolder<OrderModel>>> call, Response<ApiResponse<ItemsHolder<OrderModel>>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<ItemsHolder<OrderModel>> body = response.body();
                    if (body.result.items.size() != 0) {
                        ordersRecyclerViewTitle.setVisibility(View.VISIBLE);
                        data.clear();
                        data.addAll(body.result.items);
                        adapter.notifyDataSetChanged();
                        System.out.println(new Gson().toJson(adapter.data));
                        return;
                    } else {
                        noOrdersTextView.setVisibility(View.VISIBLE);
                        return;
                    }
                }
                Toast.makeText(OrdersActivity.this, "Request was not succesful. Code: " + response.code(), Toast.LENGTH_SHORT).show();
            }

            public void onFailure(Call<ApiResponse<ItemsHolder<OrderModel>>> call, Throwable t) {
                Log.e("dsdsd", "onFailure: ", t);
            }
        });
    }

    public void getOrderTypesData() {
        Api.getService().orderTypes().enqueue(new Callback<ApiResponse<ItemsHolder<OrderTypeModel>>>() {
            @Override
            public void onResponse(Call<ApiResponse<ItemsHolder<OrderTypeModel>>> call, Response<ApiResponse<ItemsHolder<OrderTypeModel>>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<ItemsHolder<OrderTypeModel>> body = response.body();
                    if (body.success) {
                        orderTypes.clear();
                        orderTypes.addAll(body.result.items);
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<ItemsHolder<OrderTypeModel>>> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }

    public void getTerminalPointsData() {
        Api.getService().terminalPoints().enqueue(new Callback<ApiResponse<ItemsHolder<TerminalPointsModel>>>() {
            @Override
            public void onResponse(Call<ApiResponse<ItemsHolder<TerminalPointsModel>>> call, Response<ApiResponse<ItemsHolder<TerminalPointsModel>>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<ItemsHolder<TerminalPointsModel>> body = response.body();
                    if (body.success) {
                        terminalPoints.clear();
                        terminalPoints.addAll(body.result.items);
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<ItemsHolder<TerminalPointsModel>>> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }


}
