package com.start.laundryapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import okhttp3.OkHttpClient;

public class Sifarish_novu extends AppCompatActivity {


    public String url = "http://138.201.157.254:8017/api/services/app/orderType/all";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sifarish_novu);



    }
}
