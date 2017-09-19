package com.start.laundryapp;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class YeniSifarish extends AppCompatActivity implements Spinner.OnItemSelectedListener {

    EditText yenisifarish_paltarsayi, yenisifarish_achiqlama, yenisifarish_qeyd;
    Button sifarishver_btn;
    ImageView yenisifarish_addphoto_btn;
    RequestQueue requestQueue;
    Spinner yenisifarish_menteqe_sp, yenisifarish_sifarishnovu_sp, yenisifarish_icramuddeti_sp;
    String sifarishVer_url = "http://138.201.157.254:8017/api/services/app/order/create";
    public static final String icraNovleri_url = "http://138.201.157.254:8017/api/services/app/orderExecutionType/all";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yeni_sifarish);

        yenisifarish_paltarsayi = (EditText) findViewById(R.id.yenisifarish_paltarsayi_et);
        yenisifarish_achiqlama = (EditText) findViewById(R.id.yenisifarish_achiqlama_et);
        yenisifarish_menteqe_sp = (Spinner) findViewById(R.id.yenisifarish_menteqe_sp);
        yenisifarish_sifarishnovu_sp = (Spinner) findViewById(R.id.yenisifarish_sifarishnovu_sp);
        yenisifarish_icramuddeti_sp = (Spinner) findViewById(R.id.yenisifarish_icramuddeti_sp);
        yenisifarish_qeyd = (EditText) findViewById(R.id.yenisifarish_qeyd_et);
        sifarishver_btn = (Button) findViewById(R.id.sifarishver_btn);
        yenisifarish_addphoto_btn = (ImageView) findViewById(R.id.yenisifarish_addphoto_btn);

        getData();
    }


    private void getData() {

        requestQueue = Volley.newRequestQueue(this);

        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(icraNovleri_url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONObject jsonObject = new JSONObject("response");
                    JSONArray jsonArray = jsonObject.getJSONArray("items");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String name = object.getString("name");

                        Log.i("name", name);


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(jsonArrayRequest);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {


    }
}
