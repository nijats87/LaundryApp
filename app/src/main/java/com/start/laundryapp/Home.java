package com.start.laundryapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Home extends AppCompatActivity {

    ImageView home_newOrder_icon, home_orders_icon, home_checkOrder_icon, home_payment_icon, home_personalinfo_icon, home_feedback_icon, home_settings_icon, home_exit_icon;
    TextView home_nameSurname_tv;
    public final String TAG = "LAUNDRY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        home_newOrder_icon = findViewById(R.id.home_newOrder_icon);
        home_orders_icon = findViewById(R.id.home_orders_icon);
        home_checkOrder_icon = findViewById(R.id.home_checkOrder_icon);
        home_payment_icon = findViewById(R.id.home_payment_icon);
        home_personalinfo_icon = findViewById(R.id.home_personalinfo_icon);
        home_feedback_icon = findViewById(R.id.home_feedback_icon);
        home_settings_icon = findViewById(R.id.home_settings_icon);
        home_exit_icon = findViewById(R.id.home_exit_icon);

        home_nameSurname_tv = findViewById(R.id.home_nameSurname_tv);

        SharedPreferences myPref = PreferenceManager.getDefaultSharedPreferences(this);
        String name = myPref.getString("userName", "name");
        String surname = myPref.getString("userSurname", "surname");


        Typeface facile_font = Typeface.createFromAsset(getAssets(), "fonts/FacileSans.otf");
        home_nameSurname_tv.setTypeface(facile_font);

        home_nameSurname_tv.setText(name + " " + surname);


        home_newOrder_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, MakeOrder.class);
                startActivity(intent);
            }
        });

        home_orders_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, OrdersActivity.class);
                startActivity(intent);
            }
        });

        home_personalinfo_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, PersonalInfo.class);
                startActivity(intent);
            }
        });

        home_settings_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Settings.class);
                startActivity(intent);
            }
        });

        home_exit_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG,  "removed TOKEN: " + SharedPrefs.getToken());
                SharedPrefs.removeToken();
                finish();
                startActivity(new Intent(Home.this, MainActivity.class));
            }
        });


    }
}
