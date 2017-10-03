package com.start.laundryapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.ArrayMap;
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
import com.start.laundryapp.models.ClothesModel;
import com.start.laundryapp.models.ClothesTypeModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.start.laundryapp.ServerAdress.server_URL;

public class EditClothesActivity extends AppCompatActivity {

    ImageView croppedImage;
    Spinner clothesTypesSpinner;
    EditText notes_et;
    Button cancelBtn, doneBtn;
    RequestQueue requestQueue;
    public String CLOTHES_TYPES_URL = server_URL + "api/services/app/clothesType/all";
    private List<ClothesTypeModel> clothesNames;
    private ArrayAdapter<String> clothesTypesAdapter;
    private List<String> clothesTypeAz;

    ClothesModel clothesModel = new ClothesModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothes_type);

        getClothesTypesData();
        croppedImage = (ImageView) findViewById(R.id.croppedImage);
        clothesTypesSpinner = (Spinner) findViewById(R.id.clothesTypesSpinner);
        notes_et = (EditText) findViewById(R.id.notes_et);
        cancelBtn = (Button) findViewById(R.id.cancelBtn);
        doneBtn = (Button) findViewById(R.id.doneBtn);

        clothesNames = new ArrayList<>();
        clothesTypeAz = new ArrayList<>();

        Intent intent = getIntent();
        clothesModel.imageUri = intent.getStringExtra("croppedImgURI");
        croppedImage.setImageURI(Uri.parse(clothesModel.imageUri));

        clothesTypesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, clothesTypeAz);
        clothesTypesAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        clothesTypesSpinner.setAdapter(clothesTypesAdapter);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                setResult(RESULT_CANCELED, data);
                finish();
            }
        });

        doneBtn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                //TODO: validate
                clothesModel.clothTypeId = clothesNames.get(clothesTypesSpinner.getSelectedItemPosition()).getId();
                clothesModel.note = notes_et.getText().toString();
                Intent data = new Intent();
                data.putExtra("clothesModel", new Gson().toJson(clothesModel));
                setResult(RESULT_OK, data);
                finish();
            }
        });


    }
    private void getClothesTypesData() {

        requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, CLOTHES_TYPES_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject result = jsonObject.getJSONObject("result");
                    JSONArray items = result.getJSONArray("items");
                    for (int i = 0; i < items.length(); i++) {
                        ClothesTypeModel clothesTypeModel = new ClothesTypeModel();
                        JSONObject item = items.getJSONObject(i);
                        clothesTypeModel.setName(item.getString("name"));
                        clothesTypeModel.setNameAz(item.getString("nameAz"));
                        clothesTypeModel.setNameRu(item.getString("nameRu"));
                        clothesTypeModel.setOrdinal(item.getInt("ordinal"));
                        clothesTypeModel.setId(item.getInt("id"));
                        clothesNames.add(clothesTypeModel);
                        clothesTypeAz.add(clothesTypeModel.getNameAz());
                    }

                    clothesTypesAdapter.notifyDataSetChanged();

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



