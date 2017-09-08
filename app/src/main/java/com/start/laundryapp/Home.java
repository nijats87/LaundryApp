package com.start.laundryapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Home extends AppCompatActivity {

    ImageView home_yenisifarish_icon, home_sifarishler_icon, home_sifarishizle_icon, home_odenish_icon, home_personalinfo_icon, home_feedback_icon, home_ayarlar_icon, home_chixish_icon;
    TextView nameSurname_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        home_yenisifarish_icon = (ImageView) findViewById(R.id.home_yenisifarish_icon);
        home_sifarishler_icon = (ImageView) findViewById(R.id.home_sifarishler_icon);
        home_sifarishizle_icon = (ImageView) findViewById(R.id.home_sifarishizle_icon);
        home_odenish_icon = (ImageView) findViewById(R.id.home_odenish_icon);
        home_personalinfo_icon = (ImageView) findViewById(R.id.home_personalinfo_icon);
        home_feedback_icon = (ImageView) findViewById(R.id.home_feedback_icon);
        home_ayarlar_icon = (ImageView) findViewById(R.id.home_ayarlar_icon);
        home_chixish_icon = (ImageView) findViewById(R.id.home_chixish_icon);

        nameSurname_tv = (TextView) findViewById(R.id.home_adSoyad_tv);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String surname = intent.getStringExtra("surname");

        Typeface facile_font = Typeface.createFromAsset(getAssets(), "fonts/FacileSans.otf");
        nameSurname_tv.setTypeface(facile_font);

        nameSurname_tv.setText(name + " " + surname);


        home_yenisifarish_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, YeniSifarish.class);
                startActivity(intent);
            }
        });

        home_personalinfo_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Personal_info.class);
                startActivity(intent);
            }
        });

        home_ayarlar_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Tenzimlemeler.class);
                startActivity(intent);
            }
        });


    }
}
