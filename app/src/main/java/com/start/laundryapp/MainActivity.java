package com.start.laundryapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView main_AppName_tv, main_Login_tv, main_Register_tv;
    ImageView main_AppLoqo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPrefs.init(this);
        if (SharedPrefs.getToken() != null) {
            startActivity(new Intent(this, Home.class));
            finish();
        }

        setContentView(R.layout.activity_main);

        main_AppName_tv = findViewById(R.id.main_AppName_tv);
        main_Login_tv = findViewById(R.id.main_Login_tv);
        main_Register_tv = findViewById(R.id.main_Register_tv);
//        main_Haqqimizda_tv = (TextView)findViewById(R.id.main_Haqqimizda_tv);

        main_AppLoqo = findViewById(R.id.main_AppLogo);

        Typeface facile_font = Typeface.createFromAsset(getAssets(), "fonts/FacileSans.otf");

        main_AppName_tv.setTypeface(facile_font);
        main_Login_tv.setTypeface(facile_font);
        main_Register_tv.setTypeface(facile_font);
//        main_Haqqimizda_tv.setTypeface(facile_font);
//
//        main_Haqqimizda_tv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, About.class);
//                startActivity(intent);
//            }
//        });

        main_Register_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
            }
        });

        main_Login_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
            }
        });



    }
}
