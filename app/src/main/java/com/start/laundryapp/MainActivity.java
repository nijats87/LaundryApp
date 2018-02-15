package com.start.laundryapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.start.laundryapp.retrofit.SharedPrefs;

public class MainActivity extends Activity {

    TextView main_AppName_tv, main_Login_tv, main_Register_tv;
    ImageView main_AppLoqo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);


        SharedPrefs.init(this);
        if (SharedPrefs.getToken() != null) {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        }

        setContentView(R.layout.activity_main);


        main_AppName_tv = findViewById(R.id.main_AppName_tv);
        main_Login_tv = findViewById(R.id.main_Login_tv);
        main_Register_tv = findViewById(R.id.main_Register_tv);

        main_AppLoqo = findViewById(R.id.main_AppLogo);

        Typeface facile_font = Typeface.createFromAsset(getAssets(), "fonts/FacileSans.otf");

        main_AppName_tv.setTypeface(facile_font);
        main_Login_tv.setTypeface(facile_font);
        main_Register_tv.setTypeface(facile_font);


        main_Register_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        main_Login_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });



    }
}
