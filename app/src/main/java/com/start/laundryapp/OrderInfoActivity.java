package com.start.laundryapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.start.laundryapp.models.ExecutionTypeModel;
import com.start.laundryapp.models.OrderModel;
import com.start.laundryapp.models.OrderTypeModel;
import com.start.laundryapp.models.TerminalPointsModel;

public class OrderInfoActivity extends AppCompatActivity {
    OrderModel orderModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_info);
        Intent intent = getIntent();
        orderModel = new Gson().fromJson(intent.getStringExtra("orderModel"), OrderModel.class);


        ((TextView)findViewById(R.id.orderInfo_orderNumber)).setText("Sifariş #" + orderModel.id);

        ((TextView)findViewById(R.id.orderInfo_clothesCount_tv)).setText(orderModel.numberOfClothes + "");

        for (TerminalPointsModel model : Home.terminalPoints) {
            if (model.getId() == orderModel.terminalPointId) {
                ((TextView)findViewById(R.id.orderInfo_terminalPoint_tv)).setText(model.getName());
            }
        }

        for (OrderTypeModel model : Home.orderTypes) {
            if (model.getId() == orderModel.orderTypeId) {
                ((TextView)findViewById(R.id.orderInfo_orderType_tv)).setText(model.getNameAz());
            }
        }

        for (ExecutionTypeModel model : Home.executionTypes) {
            if (model.getId() == orderModel.executionTypeId) {
                ((TextView)findViewById(R.id.orderInfo_executionType_tv)).setText(model.getNameAz());
            }
        }

        ((TextView)findViewById(R.id.orderInfo_note_et)).setText(orderModel.notes);

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

    }
}
