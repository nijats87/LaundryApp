package com.start.laundryapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.start.laundryapp.models.ApiResponse;
import com.start.laundryapp.models.RegisterModel;
import com.start.laundryapp.models.RegisterResultModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {

    EditText register_name_et, register_surname_et, register_email_et, register_phone_et, register_password_et, register_address_et;
    Button register_btn;
    CheckBox register_checkbox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register_name_et = findViewById(R.id.register_name_et);
        register_surname_et = findViewById(R.id.register_surname_et);
        register_email_et = findViewById(R.id.register_email_et);
        register_phone_et = findViewById(R.id.register_phone_et);
        register_password_et = findViewById(R.id.register_password_et);
        register_address_et = findViewById(R.id.register_address_et);
        register_checkbox = findViewById(R.id.register_checkbox);

        final String shifre = register_password_et.getText().toString();

        register_btn = findViewById(R.id.register_btn);

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

                final RegisterModel registerModel = new RegisterModel();

                registerModel.name = register_name_et.getText().toString();
                registerModel.surname = register_surname_et.getText().toString();
                registerModel.emailAddress = register_email_et.getText().toString();
                registerModel.phoneNumber = register_phone_et.getText().toString();
                registerModel.password = register_password_et.getText().toString();
                registerModel.homeAddress = register_address_et.getText().toString();


                if (!register_checkbox.isChecked())
                    YoYo.with(Techniques.Shake)
                            .duration(600)
                            .repeat(1)
                            .playOn(register_checkbox);
                else {
                    Api.getService().register(registerModel).enqueue(new Callback<ApiResponse<RegisterResultModel>>() {
                        @Override
                        public void onResponse(Call<ApiResponse<RegisterResultModel>> call, Response<ApiResponse<RegisterResultModel>> response) {
                            if (response.isSuccessful()) {
                                ApiResponse<RegisterResultModel> body = response.body();
                                if (body.success) {
                                    switch (body.result.registerResult) {
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
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ApiResponse<RegisterResultModel>> call, Throwable t) {

                        }
                    });

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


