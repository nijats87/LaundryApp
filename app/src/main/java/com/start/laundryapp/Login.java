package com.start.laundryapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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


public class Login extends AppCompatActivity {

    EditText login_emailPhone_et, login_password_et;
    TextView login_forgetPassword_tv;
    Button login_btn;
    ImageView facebook_login_img;
    RequestQueue requestQueue;
    String login_url = server_URL + "api/Account/Authenticate";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        login_emailPhone_et = (EditText) findViewById(R.id.login_emailPhone_et);

        login_password_et = (EditText) findViewById(R.id.login_password_et);


        login_forgetPassword_tv = (TextView) findViewById(R.id.login_forgetPassword_tv);
        login_btn = (Button) findViewById(R.id.login_btn);
        facebook_login_img = (ImageView) findViewById(R.id.facebook_login_img);

        requestQueue = Volley.newRequestQueue(this);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (login_emailPhone_et.getText().toString().matches("")) {
                    YoYo.with(Techniques.Shake)
                            .duration(600)
                            .repeat(1)
                            .playOn(login_emailPhone_et);
                    return;
                }
                if (login_password_et.getText().toString().matches("")) {
                    YoYo.with(Techniques.Shake)
                            .duration(600)
                            .repeat(1)
                            .playOn(login_password_et);
                } else {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, login_url, new Response.Listener<String>() {
                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONObject result = jsonObject.getJSONObject("result");
                                JSONObject userData = null;
                                if (!(result.isNull("userData"))) {
                                    userData = result.getJSONObject("userData");
                                }
                                int loginResult = (int) result.get("loginResult");
                                String name = "";
                                String surname = "";
                                String accessToken = "";

                                Log.i("result", String.valueOf(result));

                                if (userData == null) {
                                    System.out.println("userData is: " + null);

                                } else {
                                    name = (String) userData.get("name");
                                    surname = (String) userData.get("surname");
                                    accessToken = result.getString("accessToken");
                                    SharedPreferences.Editor myPref = getSharedPreferences("accessToken", MODE_PRIVATE).edit();
                                    myPref.putString("Authorization", accessToken);
                                    myPref.apply();
                                }

                                Log.i("accessToken", accessToken);

                                switch (loginResult) {
                                    case 1:
                                        Intent intent = new Intent(Login.this, Home.class);
                                        intent.putExtra("name", name);
                                        intent.putExtra("surname", surname);
                                        startActivity(intent);
                                        break;
                                    case 2:
                                        Toast.makeText(Login.this, "InvalidEmailAddressOrPhoneNumber", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 3:
                                        Toast.makeText(Login.this, "InvalidPassword", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 4:
                                        Toast.makeText(Login.this, "UserIsNotActive", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 5:
                                        Toast.makeText(Login.this, "InvalidTenancyName", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 6:
                                        Toast.makeText(Login.this, "TenantIsNotActive", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 7:
                                        Toast.makeText(Login.this, "UserEmailIsNotConfirmed", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 8:
                                        Toast.makeText(Login.this, "UnknownExternalLogin", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 9:
                                        Toast.makeText(Login.this, "LockedOut", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 10:
                                        Toast.makeText(Login.this, "UserPhoneNumberIsNotConfirmed", Toast.LENGTH_SHORT).show();
                                        break;
                                    default:
                                        Toast.makeText(Login.this, "UnknownError", Toast.LENGTH_SHORT).show();
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
                            params.put("emailAddressOrPhoneNumber", login_emailPhone_et.getText().toString());
                            params.put("password", login_password_et.getText().toString());
                            return params;
                        }
                    };
                    requestQueue.add(stringRequest);
                }
            }
        });
    }

    public void customToast() {
        Toast toast = Toast.makeText(getApplicationContext(), "Daxil edilən məlumatlar yalnışdır", Toast.LENGTH_LONG);
//        View view = toast.getView();
//        view.setBackgroundResource(R.drawable.toastborder);
        toast.setGravity(Gravity.BOTTOM, 0, 50);
//        toast.setView(view);
        toast.show();
    }
}
