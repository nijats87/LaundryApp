package com.start.laundryapp;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.start.laundryapp.ServerAdress.server_URL;

public class YeniSifarish extends AppCompatActivity {

    EditText yenisifarish_paltarsayi, yenisifarish_qeyd;
    Button sifarishver_btn;
    ImageView yenisifarish_addphoto_btn;
    RequestQueue requestQueue;
    Spinner yenisifarish_menteqe_sp, yenisifarish_sifarishnovu_sp, yenisifarish_icramuddeti_sp;
    public String sifarishVer_url = server_URL + "api/services/app/order/create";
    public String icraNovleri_url = server_URL + "api/services/app/orderExecutionType/all";
    public String sifarishNovu_url = server_URL + "api/services/app/orderType/all";
    public String menteqeNovu_url = server_URL + "api/services/app/terminalPoint/all";
    private List<String> icraNovleri;
    private List<String> sifarishNovleri;
    private List<String> menteqeNovleri;
    private ArrayAdapter<String> icraNovleriAdapter;
    private ArrayAdapter<String> sifarishNovleriAdapter;
    private ArrayAdapter<String> menteqeNovleriAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yeni_sifarish);

        yenisifarish_paltarsayi = (EditText) findViewById(R.id.yenisifarish_paltarsayi_et);
        yenisifarish_menteqe_sp = (Spinner) findViewById(R.id.yenisifarish_menteqe_sp);
        yenisifarish_sifarishnovu_sp = (Spinner) findViewById(R.id.yenisifarish_sifarishnovu_sp);
        yenisifarish_qeyd = (EditText) findViewById(R.id.yenisifarish_qeyd_et);
        yenisifarish_icramuddeti_sp = (Spinner) findViewById(R.id.yenisifarish_icramuddeti_sp);
        sifarishver_btn = (Button) findViewById(R.id.sifarishver_btn);
        yenisifarish_addphoto_btn = (ImageView) findViewById(R.id.yenisifarish_addphoto_btn);

        icraNovleri = new ArrayList<>();
        sifarishNovleri = new ArrayList<>();
        menteqeNovleri = new ArrayList<>();

        icraNovleriAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, icraNovleri);
        icraNovleriAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yenisifarish_icramuddeti_sp.setAdapter(icraNovleriAdapter);

        sifarishNovleriAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sifarishNovleri);
        sifarishNovleriAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yenisifarish_sifarishnovu_sp.setAdapter(sifarishNovleriAdapter);

        menteqeNovleriAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, menteqeNovleri);
        menteqeNovleriAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yenisifarish_menteqe_sp.setAdapter(menteqeNovleriAdapter);

        getOrderTypesData();
        getExecutionTypesData();
        getTerminalPointsData();


        yenisifarish_addphoto_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Button", "dasfsa");
            }
        });
    }

    private void getExecutionTypesData() {

        requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, icraNovleri_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    System.out.println(jsonObject);
                    JSONObject result = jsonObject.getJSONObject("result");
                    JSONArray items = result.getJSONArray("items");

                    for (int i = 0; i < items.length(); i++) {
                        JSONObject item = items.getJSONObject(i);
                        String name = item.getString("name");
                        String nameAz = item.getString("nameAz");
                        String nameRu = item.getString("nameRu");
                        String id = item.getString("id");

                        icraNovleri.add(nameAz);
                    }
                    icraNovleriAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(stringRequest);

    }

    private void getOrderTypesData() {

        requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, sifarishNovu_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.i("response", response);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject result = jsonObject.getJSONObject("result");
                    JSONArray items = result.getJSONArray("items");

                    for (int i=0; i< items.length(); i++){
                        JSONObject item = items.getJSONObject(i);
                        String name = item.getString("name");
                        String nameAz = item.getString("nameAz");
                        String nameRu = item.getString("nameRu");
                        String id = item.getString("id");

                        sifarishNovleri.add(nameAz);
                    }
                    sifarishNovleriAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> mHeader = new ArrayMap<String, String>();

                SharedPreferences myPref = getSharedPreferences("accessToken", MODE_PRIVATE);
                String accessToken = myPref.getString("Authorization", null);

                mHeader.put("Authorization", "Bearer " + accessToken);
                return mHeader;

            }
        };

        requestQueue.add(stringRequest);

    }

    private void getTerminalPointsData() {

        requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, menteqeNovu_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.i("response", response);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject result = jsonObject.getJSONObject("result");
                    JSONArray items = result.getJSONArray("items");

                    for (int i=0; i< items.length(); i++){
                        JSONObject item = items.getJSONObject(i);
                        String name = item.getString("name");
                        String nameAz = item.getString("nameAz");
                        String nameRu = item.getString("nameRu");
                        String id = item.getString("id");
                        int longitude = item.getInt("longitude");
                        int latitude = item.getInt("latitude");

                        menteqeNovleri.add(name);
                    }
                    menteqeNovleriAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> mHeader = new ArrayMap<String, String>();

                SharedPreferences myPref = getSharedPreferences("accessToken", MODE_PRIVATE);
                String accessToken = myPref.getString("Authorization", null);

                mHeader.put("Authorization", "Bearer " + accessToken);
                return mHeader;

            }
        };

        requestQueue.add(stringRequest);
    }

}
