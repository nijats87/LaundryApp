package com.start.laundryapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.start.laundryapp.adapters.BaseRecyclerAdapter;
import com.start.laundryapp.adapters.OrderClothesInfoRecyclerAdapter;
import com.start.laundryapp.models.ClothesModel;
import com.start.laundryapp.models.OrderModel;

import java.util.List;

public class OrderClothesInfoActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    OrderClothesInfoRecyclerAdapter adapter = new OrderClothesInfoRecyclerAdapter(this, new BaseRecyclerAdapter.OnClickListener<ClothesModel>() {
        @Override
        public void onClick(ClothesModel model, int position) {

        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_clothes_info);

        Intent intent = getIntent();

        OrderModel orderModel = new Gson().fromJson(intent.getStringExtra("orderModel"), OrderModel.class);
        adapter.data =  orderModel.clothes;

        ((TextView) findViewById(R.id.orderClothesNumber)).setText("Sifariş #" + orderModel.id);

        recyclerView = findViewById(R.id.recyclerViewOrderClothes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}
