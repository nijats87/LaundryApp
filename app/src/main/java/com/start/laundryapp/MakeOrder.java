package com.start.laundryapp;

import android.content.Intent;
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

public class MakeOrder extends AppCompatActivity {

    EditText makeOrder_clothesCount_et, makeOrder_note_et;
    Button makeOrder_btn;
    ImageView makeOrder_addPhoto_btn;
    RequestQueue requestQueue;
    Spinner makeOrder_terminalPoint_sp, makeOrder_orderType_sp, makeOrder_executionType_sp;
    public String MAKE_ORDER_URL = server_URL + "api/services/app/order/create";
    public String EXECUTON_TYPES_URL = server_URL + "api/services/app/orderExecutionType/all";
    public String ORDER_TYPES_URL = server_URL + "api/services/app/orderType/all";
    public String TERMINAL_POINTS_URL = server_URL + "api/services/app/terminalPoint/all";
    private List<String> executionTypes;
    private List<String> orderTypes;
    private List<String> terminalPoints;
    private ArrayAdapter<String> executionTypesAdapter;
    private ArrayAdapter<String> orderTypesAdapter;
    private ArrayAdapter<String> terminalPointsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_order);

        makeOrder_clothesCount_et = (EditText) findViewById(R.id.makeOrder_clothesCount_et);
        makeOrder_terminalPoint_sp = (Spinner) findViewById(R.id.makeOrder_terminalPoint_sp);
        makeOrder_orderType_sp = (Spinner) findViewById(R.id.makeOrder_orderType_sp);
        makeOrder_note_et = (EditText) findViewById(R.id.makeOrder_note_et);
        makeOrder_executionType_sp = (Spinner) findViewById(R.id.makeOrder_executionType_sp);
        makeOrder_btn = (Button) findViewById(R.id.makeOrder_btn);
        makeOrder_addPhoto_btn = (ImageView) findViewById(R.id.makeOrder_addPhoto_btn);

        executionTypes = new ArrayList<>();
        orderTypes = new ArrayList<>();
        terminalPoints= new ArrayList<>();

        executionTypesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, executionTypes);
        executionTypesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        makeOrder_executionType_sp.setAdapter(executionTypesAdapter);

        orderTypesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, orderTypes);
        orderTypesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        makeOrder_orderType_sp.setAdapter(orderTypesAdapter);

        terminalPointsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, terminalPoints);
        terminalPointsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        makeOrder_terminalPoint_sp.setAdapter(terminalPointsAdapter);

        getOrderTypesData();
        getExecutionTypesData();
        getTerminalPointsData();


        makeOrder_addPhoto_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MakeOrder.this, ClothesActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getExecutionTypesData() {

        requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, EXECUTON_TYPES_URL, new Response.Listener<String>() {
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

                        executionTypes.add(nameAz);
                    }
                    executionTypesAdapter.notifyDataSetChanged();

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

        StringRequest stringRequest = new StringRequest(Request.Method.GET, ORDER_TYPES_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.i("response", response);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject result = jsonObject.getJSONObject("result");
                    JSONArray items = result.getJSONArray("items");

                    for (int i = 0; i < items.length(); i++) {
                        JSONObject item = items.getJSONObject(i);
                        String name = item.getString("name");
                        String nameAz = item.getString("nameAz");
                        String nameRu = item.getString("nameRu");
                        String id = item.getString("id");

                        orderTypes.add(nameAz);
                    }
                    orderTypesAdapter.notifyDataSetChanged();

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

        StringRequest stringRequest = new StringRequest(Request.Method.GET, TERMINAL_POINTS_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.i("response", response);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject result = jsonObject.getJSONObject("result");
                    JSONArray items = result.getJSONArray("items");

                    for (int i = 0; i < items.length(); i++) {
                        JSONObject item = items.getJSONObject(i);
                        String name = item.getString("name");
                        String nameAz = item.getString("nameAz");
                        String nameRu = item.getString("nameRu");
                        String id = item.getString("id");
                        int longitude = item.getInt("longitude");
                        int latitude = item.getInt("latitude");

                        terminalPoints.add(name);
                    }
                    terminalPointsAdapter.notifyDataSetChanged();

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
