package com.start.laundryapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.ReferenceQueue;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

public class Qeydiyyat extends AppCompatActivity {

    EditText qeydiyyat_ad_et, qeydiyyat_soyad_et, qeydiyyat_email_et, qeydiyyat_telefon_et, qeydiyyat_shifre_et, qeydiyyat_unvan_et;
    Button qeydiyyat_btn;
    String qeydiyyat_url = "http://138.201.157.254:8017/api/Account/Register";
    RequestQueue requestQueue;
    CheckBox qeydiyyat_checkbox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qeydiyyat);

        qeydiyyat_ad_et = (EditText) findViewById(R.id.qeydiyyat_ad_et);
        qeydiyyat_soyad_et = (EditText) findViewById(R.id.qeydiyyat_soyad_et);
        qeydiyyat_email_et = (EditText) findViewById(R.id.qeydiyyat_email_et);
        qeydiyyat_telefon_et = (EditText) findViewById(R.id.qeydiyyat_telefon_et);
        qeydiyyat_shifre_et = (EditText) findViewById(R.id.qeydiyyat_shifre_et);
        qeydiyyat_unvan_et = (EditText) findViewById(R.id.qeydiyyat_unvan_et);
        qeydiyyat_checkbox = (CheckBox) findViewById(R.id.qeydiyyat_checkbox);

        final String shifre = qeydiyyat_shifre_et.getText().toString();

        qeydiyyat_btn = (Button) findViewById(R.id.qeydiyyat_btn);

        requestQueue = Volley.newRequestQueue(this);


        qeydiyyat_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (checkEditText(qeydiyyat_ad_et, "Ad"))
                        return;
                    if (checkEditText(qeydiyyat_soyad_et, "Soyad"))
                        return;
                    if (checkEditText(qeydiyyat_email_et, "Email"))
                        return;
                    if (checkEditText(qeydiyyat_telefon_et, "Telefon"))
                        return;
                    if (checkEditText(qeydiyyat_shifre_et, "Şifrə"))
                        return;
                    if (checkEditText(qeydiyyat_unvan_et, "Ünvan"))
                        return;

//                if (!qeydiyyat_checkbox.isChecked() || qeydiyyat_ad_et.getText().toString().matches("") || qeydiyyat_soyad_et.getText().toString().matches("") || qeydiyyat_email_et.getText().toString().matches("") ||
//                        qeydiyyat_telefon_et.getText().toString().matches("") || qeydiyyat_shifre_et.getText().toString().matches("") || qeydiyyat_unvan_et.getText().toString().matches("")) {
//                    YoYo.with(Techniques.Pulse)
//                            .duration(600)
//                            .repeat(1)
//                            .playOn(qeydiyyat_btn);
//                } else if (shifre.length() < 4) {
//                    Toast.makeText(Qeydiyyat.this, "Şifrə 3 simvoldan az ola bilməz", Toast.LENGTH_SHORT).show();
                 else {
                        if( !qeydiyyat_checkbox.isChecked())
                            YoYo.with(Techniques.Shake)
                            .duration(600)
                            .repeat(1)
                            .playOn(qeydiyyat_checkbox);
                        else{
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, qeydiyyat_url, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        JSONObject result = jsonObject.getJSONObject("result");
                                        int registerResult = result.getInt("registerResult");

                                        Log.i("registerResult", String.valueOf(registerResult));

                                        switch (registerResult) {
                                            case 1:
                                                Toast.makeText(Qeydiyyat.this, "Qeydiyyat uğurla tamamlandı.", Toast.LENGTH_SHORT).show();
                                                break;
                                            case 2:
                                                Toast.makeText(Qeydiyyat.this, "E-mail düzgün formatda deyil", Toast.LENGTH_SHORT).show();
                                                break;
                                            case 3:
                                                Toast.makeText(Qeydiyyat.this, "Telefon nömrəsi düzgün formatda deyil", Toast.LENGTH_SHORT).show();
                                                break;
                                            case 4:
                                                Toast.makeText(Qeydiyyat.this, "Şifrə düzgün deyil", Toast.LENGTH_SHORT).show();
                                                break;
                                            case 5:
                                                Toast.makeText(Qeydiyyat.this, "Bu e-mail artıq mövcuddur", Toast.LENGTH_SHORT).show();
                                                break;
                                            case 6:
                                                Toast.makeText(Qeydiyyat.this, "Bu telefon nömrəsi artıq mövcuddur", Toast.LENGTH_SHORT).show();
                                                break;
                                            default:
                                                Toast.makeText(Qeydiyyat.this, "Zəhmət olmasa məlumatları düzgün daxil edin", Toast.LENGTH_SHORT).show();
                                                break;
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    error.printStackTrace();
                                }
                            }) {
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String, String> params = new HashMap<>();
                                    params.put("name", qeydiyyat_ad_et.getText().toString());
                                    params.put("surname", qeydiyyat_soyad_et.getText().toString());
                                    params.put("emailAddress", qeydiyyat_email_et.getText().toString());
                                    params.put("phoneNumber", qeydiyyat_telefon_et.getText().toString());
                                    params.put("password", qeydiyyat_shifre_et.getText().toString());
                                    params.put("homeAddress", qeydiyyat_unvan_et.getText().toString());
                                    return params;
                                }
                            };
                            requestQueue.add(stringRequest);
                        }
                }
            }
        });
    }

    private boolean checkEditText(EditText editText , String type) {

        if (editText.getText().toString().length() < 3) {
//            if(type.equals("Ad") || type.equals("Soyad") || type.equals("Ünvan")|| type.equals("Şifrə"))
//                Toast.makeText(this, type + " 3 simvoldan az ola bilməz", Toast.LENGTH_SHORT).show();
            YoYo.with(Techniques.Shake)
                    .duration(600)
                    .repeat(1)
                    .playOn(editText);
            return true;
        }
        return false;
    }
}


