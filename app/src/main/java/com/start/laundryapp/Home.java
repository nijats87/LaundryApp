package com.start.laundryapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.start.laundryapp.models.ApiResponse;
import com.start.laundryapp.models.ClothesTypeModel;
import com.start.laundryapp.models.ExecutionTypeModel;
import com.start.laundryapp.models.ItemsHolder;
import com.start.laundryapp.models.OrderTypeModel;
import com.start.laundryapp.models.TerminalPointsModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends AppCompatActivity {

    ImageView home_newOrder_icon, home_orders_icon, home_checkOrder_icon, home_payment_icon, home_personalinfo_icon, home_feedback_icon, home_settings_icon, home_exit_icon;
    TextView home_nameSurname_tv;
    public final String TAG = "LAUNDRY";

    public static List<TerminalPointsModel> terminalPoints = new ArrayList<>();
    public static List<String> terminalPointsAz = new ArrayList<>();
    public static List<ExecutionTypeModel> executionTypes = new ArrayList<>();
    public static List<OrderTypeModel> orderTypes = new ArrayList<>();
    public static List<String> executionTypeAz = new ArrayList<>();
    public static List<String> orderTypeAz = new ArrayList<>();
    public static List<ClothesTypeModel> clothesNames = new ArrayList<>();
    public static List<String> clothesNamesAz = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .defaultDisplayImageOptions(displayImageOptions)
                .build();

        ImageLoader.getInstance().init(config);


        home_newOrder_icon = findViewById(R.id.home_newOrder_icon);
        home_orders_icon = findViewById(R.id.home_orders_icon);
        home_checkOrder_icon = findViewById(R.id.home_checkOrder_icon);
        home_payment_icon = findViewById(R.id.home_payment_icon);
        home_personalinfo_icon = findViewById(R.id.home_personalinfo_icon);
        home_feedback_icon = findViewById(R.id.home_feedback_icon);
        home_settings_icon = findViewById(R.id.home_settings_icon);
        home_exit_icon = findViewById(R.id.home_exit_icon);

        home_nameSurname_tv = findViewById(R.id.home_nameSurname_tv);

        SharedPreferences myPref = PreferenceManager.getDefaultSharedPreferences(this);
        String name = myPref.getString("userName", "name");
        String surname = myPref.getString("userSurname", "surname");


        Typeface facile_font = Typeface.createFromAsset(getAssets(), "fonts/FacileSans.otf");
        home_nameSurname_tv.setTypeface(facile_font);

        home_nameSurname_tv.setText(name + " " + surname);


        home_newOrder_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, MakeOrder.class);
                startActivity(intent);
            }
        });

        home_orders_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, OrdersActivity.class);
                startActivity(intent);
            }
        });

        home_personalinfo_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, PersonalInfo.class);
                startActivity(intent);
            }
        });

        home_settings_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Settings.class);
                startActivity(intent);
            }
        });

        home_exit_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "removed TOKEN: " + SharedPrefs.getToken());
                SharedPrefs.removeToken();
                finish();
                startActivity(new Intent(Home.this, MainActivity.class));
            }
        });

        getTerminalPointsData();
        getOrderTypesData();
        getExecutionTypesData();
        getClothesTypesData();
    }

    private void getTerminalPointsData() {
        Api.getService().terminalPoints().enqueue(new Callback<ApiResponse<ItemsHolder<TerminalPointsModel>>>() {
            @Override
            public void onResponse(Call<ApiResponse<ItemsHolder<TerminalPointsModel>>> call, Response<ApiResponse<ItemsHolder<TerminalPointsModel>>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<ItemsHolder<TerminalPointsModel>> body = response.body();
                    if (body.success) {
                        terminalPoints = body.result.items;
                        System.out.println(terminalPoints);
                        terminalPointsAz.clear();
                        for (TerminalPointsModel point : terminalPoints) {
                            terminalPointsAz.add(point.getName());
                        }
//                        terminalPointsAdapter.notifyDataSetChanged();
                        return;
                    }
                }

                Toast.makeText(Home.this, "Request was not succesful. Code: " + response.code(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ApiResponse<ItemsHolder<TerminalPointsModel>>> call, Throwable t) {
                Toast.makeText(Home.this, "Request was not succesful.", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }

    private void getOrderTypesData() {
        Api.getService().orderTypes().enqueue(new Callback<ApiResponse<ItemsHolder<OrderTypeModel>>>() {
            @Override
            public void onResponse(Call<ApiResponse<ItemsHolder<OrderTypeModel>>> call, Response<ApiResponse<ItemsHolder<OrderTypeModel>>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<ItemsHolder<OrderTypeModel>> body = response.body();
                    if (body.success) {
                        orderTypes = body.result.items;
                        orderTypeAz.clear();
                        for (OrderTypeModel type : orderTypes) {
                            orderTypeAz.add(type.getNameAz());
                        }

//                        orderTypesAdapter.notifyDataSetChanged();
                        return;
                    }
                }

                Toast.makeText(Home.this, "Request was not succesful. Code: " + response.code(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ApiResponse<ItemsHolder<OrderTypeModel>>> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }

    private void getExecutionTypesData() {
        Api.getService().executionTypes().enqueue(new Callback<ApiResponse<ItemsHolder<ExecutionTypeModel>>>() {
            @Override
            public void onResponse(Call<ApiResponse<ItemsHolder<ExecutionTypeModel>>> call, Response<ApiResponse<ItemsHolder<ExecutionTypeModel>>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<ItemsHolder<ExecutionTypeModel>> body = response.body();
                    if (body.success) {
                        executionTypes = body.result.items;
                        executionTypeAz.clear();

                        for (ExecutionTypeModel type : executionTypes) {
                            executionTypeAz.add(type.getNameAz());
                        }
//                        executionTypesAdapter.notifyDataSetChanged();
                        return;
                    }
                }

                Toast.makeText(Home.this, "Request was not succesful. Code: " + response.code(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ApiResponse<ItemsHolder<ExecutionTypeModel>>> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }

    private void getClothesTypesData() {
        Api.getService().clothesTypes().enqueue(new Callback<ApiResponse<ItemsHolder<ClothesTypeModel>>>() {
            @Override
            public void onResponse(Call<ApiResponse<ItemsHolder<ClothesTypeModel>>> call, Response<ApiResponse<ItemsHolder<ClothesTypeModel>>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<ItemsHolder<ClothesTypeModel>> body = response.body();
                    if (body.success) {
                        clothesNames = body.result.items;

                        clothesNamesAz.clear();

                        for (int i = 0; i < clothesNames.size(); i++) {
                            ClothesTypeModel type = clothesNames.get(i);
                            clothesNamesAz.add(type.getNameAz());
                        }
                        return;
                    }
                }

                Toast.makeText(Home.this, "Request was not succesful. Code: " + response.code(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ApiResponse<ItemsHolder<ClothesTypeModel>>> call, Throwable t) {
                Log.e(EditClothesActivity.class.getSimpleName(), "onFailure: ", t);
            }
        });
    }
}
