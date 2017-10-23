package com.start.laundryapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.start.laundryapp.models.ApiResponse;
import com.start.laundryapp.models.ItemsHolder;
import com.start.laundryapp.models.OrderModel;
import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.start.laundryapp.ServerAdress.server_URL;

public class OrdersActivity extends AppCompatActivity {


    ArrayList<OrderModel> myOrders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        getMyOrders();
    }


    public void getMyOrders() {
        Call<ApiResponse<ItemsHolder<OrderModel>>> call = Api.getService().orders();

        call.enqueue(new Callback<ApiResponse<ItemsHolder<OrderModel>>>() {
            @Override
            public void onResponse(Call<ApiResponse<ItemsHolder<OrderModel>>> call, Response<ApiResponse<ItemsHolder<OrderModel>>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<ItemsHolder<OrderModel>> body = response.body();
                    if (body.success) {
                        myOrders = body.result.items;
                        return;
                    }
                }

                Toast.makeText(OrdersActivity.this, "Request was not succesful. Code: " + response.code(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ApiResponse<ItemsHolder<OrderModel>>> call, Throwable t) {
                Log.e("dsdsd", "onFailure: ", t);
            }
        });
    }
}
