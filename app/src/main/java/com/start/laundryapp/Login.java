package com.start.laundryapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.start.laundryapp.models.ApiResponse;
import com.start.laundryapp.models.LoginModel;
import com.start.laundryapp.models.LoginResultModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Login extends AppCompatActivity {

    EditText login_emailPhone_et, login_password_et;
    TextView login_forgetPassword_tv;
    Button login_btn;
    ImageView facebook_login_img;
    public final String TAG = "LAUNDRY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        login_emailPhone_et = findViewById(R.id.login_emailPhone_et);

        login_password_et = findViewById(R.id.login_password_et);


        login_forgetPassword_tv = findViewById(R.id.login_forgetPassword_tv);
        login_btn = findViewById(R.id.login_btn);
        facebook_login_img = findViewById(R.id.facebook_login_img);


        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (login_emailPhone_et.getText().toString().matches("")) {
                    YoYo.with(Techniques.Shake)
                            .duration(600)
                            .repeat(1)
                            .playOn(login_emailPhone_et);
                } else if (login_password_et.getText().toString().matches("")) {
                    YoYo.with(Techniques.Shake)
                            .duration(600)
                            .repeat(1)
                            .playOn(login_password_et);
                } else {

                    LoginModel loginModel = new LoginModel();
                    loginModel.emailAddressOrPhoneNumber = login_emailPhone_et.getText().toString();
                    loginModel.password = login_password_et.getText().toString();

                    Api.getService().login(loginModel).enqueue(new Callback<ApiResponse<LoginResultModel>>() {
                        @Override
                        public void onResponse(Call<ApiResponse<LoginResultModel>> call, Response<ApiResponse<LoginResultModel>> response) {

                            if (response.isSuccessful()) {
                                ApiResponse<LoginResultModel> body = response.body();
                                LoginResultModel result = body.result;
                                Log.e(TAG, "accessToken: " + result.accessToken);

                                switch (result.loginResult) {
                                    case 1:
                                        SharedPrefs.setToken("Bearer " + result.accessToken);
                                        SharedPreferences.Editor myPref = PreferenceManager.getDefaultSharedPreferences(Login.this).edit();
                                        myPref.putString("userName", result.userData.name);
                                        myPref.putString("userSurname", result.userData.surname);
                                        myPref.apply();
                                        Intent intent = new Intent(Login.this, Home.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
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
                            }
                        }

                        @Override
                        public void onFailure(Call<ApiResponse<LoginResultModel>> call, Throwable t) {

                        }
                    });
                }

            }
        });

    }
}
