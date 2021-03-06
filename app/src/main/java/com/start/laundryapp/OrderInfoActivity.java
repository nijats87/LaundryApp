package com.start.laundryapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.start.laundryapp.models.ApiResponse;
import com.start.laundryapp.models.ExecutionTypeModel;
import com.start.laundryapp.models.OrderModel;
import com.start.laundryapp.models.OrderTypeModel;
import com.start.laundryapp.models.TerminalPointsModel;
import com.start.laundryapp.retrofit.Api;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderInfoActivity extends AppCompatActivity {
    OrderModel orderModel;
    Button orderCancel_btn;
    TextView orderInfo_note_et;
    double longitude;
    double latitude;
    String terminalPointName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_info);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        orderCancel_btn = findViewById(R.id.orderCancel_btn);

        orderInfo_note_et = findViewById(R.id.orderInfo_note_et);

        Intent intent = getIntent();
        orderModel = new Gson().fromJson(intent.getStringExtra("orderModel"), OrderModel.class);

        switch (orderModel.status){
            case 10:
            case 20:
                orderCancel_btn.setVisibility(View.VISIBLE);
                break;
        }

        ((TextView) findViewById(R.id.orderInfo_orderNumber)).setText("Sifariş #" + orderModel.id);

        ((TextView) findViewById(R.id.orderInfo_clothesCount_tv)).setText(orderModel.numberOfClothes + "");

        for (TerminalPointsModel model : HomeActivity.terminalPoints) {
            if (model.getId() == orderModel.terminalPointId) {
                ((TextView) findViewById(R.id.orderInfo_terminalPoint_tv)).setText(model.getName());
                latitude = model.getLatitude();
                longitude = model.getLongitude();
                terminalPointName = model.getName();
            }
        }

        for (OrderTypeModel model : HomeActivity.orderTypes) {
            if (model.getId() == orderModel.orderTypeId) {
                ((TextView) findViewById(R.id.orderInfo_orderType_tv)).setText(model.getNameAz());
            }
        }

        for (ExecutionTypeModel model : HomeActivity.executionTypes) {
            if (model.getId() == orderModel.executionTypeId) {
                ((TextView) findViewById(R.id.orderInfo_executionType_tv)).setText(model.getNameAz());
            }
        }

        orderInfo_note_et.setText(orderModel.notes.matches("") ? "-----" : orderModel.notes);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(OrderInfoActivity.this, OrderClothesInfoActivity.class);
                in.putExtra("orderModel", new Gson().toJson(orderModel));
                startActivity(in);
                Log.i("NEXT", "GO TO PHOTOS");
            }
        };

        findViewById(R.id.goToPhotosBtn).setOnClickListener(listener);
        findViewById(R.id.orderInfo_clothesCount_tv).setOnClickListener(listener);

        findViewById(R.id.orderInfo_terminalPoint_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderInfoActivity.this, MapsActivity.class);
                intent.putExtra("longitude", longitude);
                intent.putExtra("latitude", latitude);
                intent.putExtra("terminalPointName", terminalPointName);
                startActivity(intent);
            }
        });


        orderCancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Api.getService().cancelOrder(orderModel.id).enqueue(new Callback<ApiResponse<Void>>() {
                    @Override
                    public void onResponse(Call<ApiResponse<Void>> call, Response<ApiResponse<Void>> response) {
                        Toast.makeText(OrderInfoActivity.this, "Status changed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ApiResponse<Void>> call, Throwable t) {
                    }
                });
            }

        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
