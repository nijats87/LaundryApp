package com.start.laundryapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.start.laundryapp.ServerAdress.server_URL;

public class Register extends AppCompatActivity {

    EditText register_name_et, register_surname_et, register_email_et, register_phone_et, register_password_et, register_address_et;
    Button register_btn;
    String regsiter_url = server_URL + "api/Account/Register";
    RequestQueue requestQueue;
    CheckBox register_checkbox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register_name_et = (EditText) findViewById(R.id.register_name_et);
        register_surname_et = (EditText) findViewById(R.id.register_surname_et);
        register_email_et = (EditText) findViewById(R.id.register_email_et);
        register_phone_et = (EditText) findViewById(R.id.register_phone_et);
        register_password_et = (EditText) findViewById(R.id.register_password_et);
        register_address_et = (EditText) findViewById(R.id.register_address_et);
        register_checkbox = (CheckBox) findViewById(R.id.register_checkbox);

        final String shifre = register_password_et.getText().toString();

        register_btn = (Button) findViewById(R.id.register_btn);

        requestQueue = Volley.newRequestQueue(this);


        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if (checkEditText(register_name_et))
                return;
            if (checkEditText(register_surname_et))
                return;
            if (checkEditText(register_email_et))
                return;
            if (checkEditText(register_phone_et))
                return;
            if (checkEditText(register_password_et))
                return;
            if (checkEditText(register_address_et))
                return;

//                if (!qeydiyyat_checkbox.isChecked() || qeydiyyat_ad_et.getText().toString().matches("") || qeydiyyat_soyad_et.getText().toString().matches("") || qeydiyyat_email_et.getText().toString().matches("") ||
//                        qeydiyyat_telefon_et.getText().toString().matches("") || qeydiyyat_shifre_et.getText().toString().matches("") || qeydiyyat_unvan_et.getText().toString().matches("")) {
//                    YoYo.with(Techniques.Pulse)
//                            .duration(600)
//                            .repeat(1)
//                            .playOn(qeydiyyat_btn);
//                } else if (shifre.length() < 4) {
//                    Toast.makeText(Register.this, "Şifrə 3 simvoldan az ola bilməz", Toast.LENGTH_SHORT).show();
                 else {
                        if( !register_checkbox.isChecked())
                            YoYo.with(Techniques.Shake)
                            .duration(600)
                            .repeat(1)
                            .playOn(register_checkbox);
                        else{
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, regsiter_url, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        JSONObject result = jsonObject.getJSONObject("result");
                                        int registerResult = result.getInt("registerResult");

                                        Log.i("registerResult", String.valueOf(registerResult));

                                        switch (registerResult) {
                                            case 1:
                                                Toast.makeText(Register.this, "Qeydiyyat uğurla tamamlandı.", Toast.LENGTH_SHORT).show();
                                                break;
                                            case 2:
                                                Toast.makeText(Register.this, "E-mail düzgün formatda deyil", Toast.LENGTH_SHORT).show();
                                                break;
                                            case 3:
                                                Toast.makeText(Register.this, "Telefon nömrəsi düzgün formatda deyil", Toast.LENGTH_SHORT).show();
                                                break;
                                            case 4:
                                                Toast.makeText(Register.this, "Şifrə düzgün deyil", Toast.LENGTH_SHORT).show();
                                                break;
                                            case 5:
                                                Toast.makeText(Register.this, "Bu e-mail artıq mövcuddur", Toast.LENGTH_SHORT).show();
                                                break;
                                            case 6:
                                                Toast.makeText(Register.this, "Bu telefon nömrəsi artıq mövcuddur", Toast.LENGTH_SHORT).show();
                                                break;
                                            default:
                                                Toast.makeText(Register.this, "Zəhmət olmasa məlumatları düzgün daxil edin", Toast.LENGTH_SHORT).show();
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
                                    params.put("name", register_name_et.getText().toString());
                                    params.put("surname", register_surname_et.getText().toString());
                                    params.put("emailAddress", register_email_et.getText().toString());
                                    params.put("phoneNumber", register_phone_et.getText().toString());
                                    params.put("password", register_password_et.getText().toString());
                                    params.put("homeAddress", register_address_et.getText().toString());
                                    return params;
                                }
                            };
                            requestQueue.add(stringRequest);
                        }
                }
            }
        });
    }

    private boolean checkEditText(EditText editText) {

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


