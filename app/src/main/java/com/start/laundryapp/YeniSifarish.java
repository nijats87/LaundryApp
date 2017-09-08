package com.start.laundryapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class YeniSifarish extends AppCompatActivity {

    EditText yenisifarish_paltarsayi, yenisifarish_achiqlama, yenisifarish_menteqe, yenisifarish_sifarishnovu, yenisifarish_icramuddeti, yenisifarish_qeyd;
    Button sifarishver_btn;
    ImageView yenisifarish_addphoto_btn;
    RequestQueue requestQueue;
    String sifarishVer_url = "http://138.201.157.254:8017/api/services/app/order/create";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yeni_sifarish);

        yenisifarish_paltarsayi = (EditText) findViewById(R.id.yenisifarish_paltarsayi_et);
        yenisifarish_achiqlama = (EditText) findViewById(R.id.yenisifarish_achiqlama_et);
        yenisifarish_menteqe = (EditText) findViewById(R.id.yenisifarish_menteqe_et);
        yenisifarish_sifarishnovu = (EditText) findViewById(R.id.yenisifarish_sifarishnovu_et);
        yenisifarish_icramuddeti = (EditText) findViewById(R.id.yenisifarish_icramuddeti_et);
        yenisifarish_qeyd = (EditText)findViewById(R.id.yenisifarish_qeyd_et);

        sifarishver_btn = (Button)findViewById(R.id.sifarishver_btn);

        yenisifarish_addphoto_btn = (ImageView)findViewById(R.id.yenisifarish_addphoto_btn);

        requestQueue = Volley.newRequestQueue(this);



        sifarishver_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, sifarishVer_url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map <String, String> params = new HashMap<>();
                        params.put("numberOfClothes", yenisifarish_paltarsayi.getText().toString());
                        params.put("orderTypeId", yenisifarish_sifarishnovu.getText().toString());
                        params.put("executionTypeId", yenisifarish_icramuddeti.getText().toString());
                        params.put("terminalPointId", yenisifarish_menteqe.getText().toString());
                        params.put("notes", yenisifarish_qeyd.getText().toString());

                        return params;
                    }

                };
                requestQueue.add(stringRequest);
            }
        });

    }
}
