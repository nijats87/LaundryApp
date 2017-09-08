package com.start.laundryapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView main_AppName_tv, main_DaxilOl_tv, main_Qeydiyyat_tv, main_Haqqimizda_tv;
    ImageView main_AppLoqo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        main_AppName_tv = (TextView)findViewById(R.id.main_AppName_tv);
        main_DaxilOl_tv = (TextView)findViewById(R.id.main_DaxilOl_tv);
        main_Qeydiyyat_tv = (TextView)findViewById(R.id.main_Qeydiyyat_tv);
//        main_Haqqimizda_tv = (TextView)findViewById(R.id.main_Haqqimizda_tv);

        main_AppLoqo = (ImageView)findViewById(R.id.main_AppLogo);

        Typeface facile_font = Typeface.createFromAsset(getAssets(), "fonts/FacileSans.otf");

        main_AppName_tv.setTypeface(facile_font);
        main_DaxilOl_tv.setTypeface(facile_font);
        main_Qeydiyyat_tv.setTypeface(facile_font);
//        main_Haqqimizda_tv.setTypeface(facile_font);
//
//        main_Haqqimizda_tv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, Haqqimizda.class);
//                startActivity(intent);
//            }
//        });

        main_Qeydiyyat_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Qeydiyyat.class);
                startActivity(intent);
            }
        });

        main_DaxilOl_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DaxilOl.class);
                startActivity(intent);
            }
        });



    }
}
