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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.start.laundryapp.models.ApiResponse;
import com.start.laundryapp.models.LoginModel;
import com.start.laundryapp.models.LoginResultModel;
import com.start.laundryapp.retrofit.Api;
import com.start.laundryapp.retrofit.SharedPrefs;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {

    EditText login_emailPhone_et, login_password_et;
    TextView login_forgetPassword_tv;
    Button login_btn;
    ImageView facebook_login_img, imageView;
    public final String TAG = "LAUNDRY";
    ProgressBar login_progressBar;
    RelativeLayout dimLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        login_emailPhone_et = findViewById(R.id.login_emailPhone_et);
        login_password_et = findViewById(R.id.login_password_et);


        login_forgetPassword_tv = findViewById(R.id.login_forgetPassword_tv);
        login_btn = findViewById(R.id.login_btn);
        facebook_login_img = findViewById(R.id.facebook_login_img);

        login_progressBar = findViewById(R.id.login_progressBar);

        dimLayout = findViewById(R.id.dim_layout);


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

                    login_progressBar.setVisibility(View.VISIBLE);
                    dimLayout.setVisibility(View.VISIBLE);

                    Api.getService().login(loginModel).enqueue(new Callback<ApiResponse<LoginResultModel>>() {
                        @Override
                        public void onResponse(Call<ApiResponse<LoginResultModel>> call, Response<ApiResponse<LoginResultModel>> response) {

                            login_progressBar.setVisibility(View.GONE);
                            dimLayout.setVisibility(View.GONE);

                            if (response.isSuccessful()) {
                                ApiResponse<LoginResultModel> body = response.body();
                                LoginResultModel result = body.result;
                                Log.e(TAG, "accessToken: " + result.accessToken);

                                switch (result.loginResult) {
                                    case 1:
                                        SharedPrefs.setToken("Bearer " + result.accessToken);
                                        SharedPreferences.Editor myPref = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this).edit();
                                        myPref.putString("userName", result.userData.name);
                                        myPref.putString("userSurname", result.userData.surname);
                                        myPref.apply();
                                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        break;
                                    case 2:
                                        Toast.makeText(LoginActivity.this, "InvalidEmailAddressOrPhoneNumber", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 3:
                                        Toast.makeText(LoginActivity.this, "InvalidPassword", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 4:
                                        Toast.makeText(LoginActivity.this, "UserIsNotActive", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 5:
                                        Toast.makeText(LoginActivity.this, "InvalidTenancyName", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 6:
                                        Toast.makeText(LoginActivity.this, "TenantIsNotActive", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 7:
                                        Toast.makeText(LoginActivity.this, "UserEmailIsNotConfirmed", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 8:
                                        Toast.makeText(LoginActivity.this, "UnknownExternalLogin", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 9:
                                        Toast.makeText(LoginActivity.this, "LockedOut", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 10:
                                        Toast.makeText(LoginActivity.this, "UserPhoneNumberIsNotConfirmed", Toast.LENGTH_SHORT).show();
                                        break;
                                    default:
                                        Toast.makeText(LoginActivity.this, "UnknownError", Toast.LENGTH_SHORT).show();
                                        break;
                                }
                            }
                            else {
                                Toast.makeText(LoginActivity.this, "Request was not succesfull!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ApiResponse<LoginResultModel>> call, Throwable t) {
                            Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                            login_progressBar.setVisibility(View.GONE);
                            dimLayout.setVisibility(View.GONE);
                        }
                    });
                }

            }
        });

    }

    @Override
    public void onBackPressed() {
        onSupportNavigateUp();
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        this.finish();
        return true;
    }
}
