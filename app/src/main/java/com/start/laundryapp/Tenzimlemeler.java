package com.start.laundryapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Tenzimlemeler extends AppCompatActivity {

    TextView tenzim_haqqimizda_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenzimlemeler);

        tenzim_haqqimizda_tv = (TextView)findViewById(R.id.tenzim_haqqimizda_tv);

        tenzim_haqqimizda_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tenzimlemeler.this, Haqqimizda.class);
                startActivity(intent);

            }
        });
    }
}

