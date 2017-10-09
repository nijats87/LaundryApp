package com.start.laundryapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Parcelable;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.start.laundryapp.models.EditClothesModel;
import com.start.laundryapp.models.ExecutionTypeModel;
import com.start.laundryapp.models.OrderTypeModel;
import com.start.laundryapp.models.TerminalPointsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Type;
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
    private List<ExecutionTypeModel> executionTypes;
    private List<OrderTypeModel> orderTypes;
    private List<TerminalPointsModel> terminalPoints;
    private List<String> executionTypeAz;
    private List<String> orderTypeAz;
    private List<String> terminalPointsAz;
    private ArrayAdapter<String> executionTypesAdapter;
    private ArrayAdapter<String> orderTypesAdapter;
    private ArrayAdapter<String> terminalPointsAdapter;

    List<EditClothesModel> clothesModels = new ArrayList<>();


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
        terminalPoints = new ArrayList<>();
        executionTypeAz = new ArrayList<>();
        terminalPointsAz = new ArrayList<>();
        orderTypeAz = new ArrayList<>();

        executionTypesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, executionTypeAz);
        executionTypesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        makeOrder_executionType_sp.setAdapter(executionTypesAdapter);

        orderTypesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, orderTypeAz);
        orderTypesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        makeOrder_orderType_sp.setAdapter(orderTypesAdapter);

        terminalPointsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, terminalPointsAz);
        terminalPointsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        makeOrder_terminalPoint_sp.setAdapter(terminalPointsAdapter);

        getOrderTypesData();
        getExecutionTypesData();
        getTerminalPointsData();

        makeOrder_addPhoto_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MakeOrder.this, ClothesActivity.class);
                intent.putExtra("clothesModels", new Gson().toJson(clothesModels));
                startActivityForResult(intent, clothesCode);
            }
        });
    }

    int clothesCode = 23245;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == clothesCode) {
            if (resultCode == RESULT_OK) {
                Type listType = new TypeToken<ArrayList<EditClothesModel>>(){}.getType();
                clothesModels = new Gson().fromJson(data.getStringExtra("clothesModels"), listType);
                makeOrder_clothesCount_et.setText(String.valueOf(clothesModels.size()));
            }
        }
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
                        ExecutionTypeModel executionTypeModel = new ExecutionTypeModel();
                        JSONObject item = items.getJSONObject(i);
                        executionTypeModel.setName(item.getString("name"));
                        executionTypeModel.setNameAz(item.getString("nameAz"));
                        executionTypeModel.setNameRu(item.getString("nameRu"));
                        executionTypeModel.setOrdinal(item.getInt("ordinal"));
                        executionTypeModel.setId(item.getInt("id"));

                        executionTypes.add(executionTypeModel);

                        executionTypeAz.add(executionTypeModel.getNameAz());


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
                        OrderTypeModel orderTypeModel = new OrderTypeModel();
                        JSONObject item = items.getJSONObject(i);
                        orderTypeModel.setName(item.getString("name"));
                        orderTypeModel.setNameAz(item.getString("nameAz"));
                        orderTypeModel.setNameRu(item.getString("nameRu"));
                        orderTypeModel.setOrdinal(item.getInt("ordinal"));
                        orderTypeModel.setId(item.getInt("id"));


                        orderTypes.add(orderTypeModel);

                        orderTypeAz.add(orderTypeModel.getNameAz());
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
                        TerminalPointsModel terminalPointsModel = new TerminalPointsModel();
                        JSONObject item = items.getJSONObject(i);
                        terminalPointsModel.setName(item.getString("name"));
                        terminalPointsModel.setNameAz(item.getString("nameAz"));
                        terminalPointsModel.setNameRu(item.getString("nameRu"));
                        terminalPointsModel.setOrdinal(item.getInt("ordinal"));
                        terminalPointsModel.setId(item.getInt("id"));
                        terminalPointsModel.setLatitude(item.getInt("latitude"));
                        terminalPointsModel.setLongitude(item.getInt("longitude"));

                        terminalPoints.add(terminalPointsModel);

                        terminalPointsAz.add(terminalPointsModel.getName());
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
