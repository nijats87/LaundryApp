package com.start.laundryapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.start.laundryapp.models.ApiResponse;
import com.start.laundryapp.models.EditClothesModel;
import com.start.laundryapp.models.MakeOrderModel;
import com.start.laundryapp.models.OrderClothesModel;
import com.start.laundryapp.retrofit.Api;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MakeOrderActivity extends AppCompatActivity {

    private static final String TAG = MakeOrderActivity.class.getSimpleName();
    @BindView(R.id.makeOrder_orderType_sp)
    Spinner makeOrder_orderType_sp;

    @BindView(R.id.makeOrder_terminalPoint_sp)
    Spinner makeOrder_terminalPoint_sp;

    @BindView(R.id.makeOrder_executionType_sp)
    Spinner makeOrder_executionType_sp;

    @BindView(R.id.makeOrder_note_et)
    EditText makeOrder_note_et;

    @BindView(R.id.makeOrder_clothesCount_et)
    EditText makeOrder_clothesCount_et;

    @BindView(R.id.makeOrder_btn)
    Button makeOrder_btn;

    @BindView(R.id.makeOrder_addPhoto_btn)
    ImageView makeOrder_addPhoto_btn;


    private List<EditClothesModel> clothesModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_order);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayAdapter<String> terminalPointsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, HomeActivity.terminalPointsAz);
        terminalPointsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        makeOrder_terminalPoint_sp.setAdapter(terminalPointsAdapter);

        ArrayAdapter<String> executionTypesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, HomeActivity.executionTypeAz);
        executionTypesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        makeOrder_executionType_sp.setAdapter(executionTypesAdapter);

        ArrayAdapter<String> orderTypesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, HomeActivity.orderTypeAz);
        orderTypesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        makeOrder_orderType_sp.setAdapter(orderTypesAdapter);


        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MakeOrderActivity.this, ClothesActivity.class);
                intent.putExtra("clothesModels", new Gson().toJson(clothesModels));
                startActivityForResult(intent, clothesCode);
            }
        };

        makeOrder_clothesCount_et.setOnClickListener(listener);

        makeOrder_addPhoto_btn.setOnClickListener(listener);

        makeOrder_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clothesModels.size() > 0){
                    makeOrder();
                }else {
                    Toast.makeText(MakeOrderActivity.this, "Sifariş vermək üçün paltar əlavə edin", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public int clothesCode = 23245;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == clothesCode) {
            if (resultCode == RESULT_OK) {
                Type listType = new TypeToken<ArrayList<EditClothesModel>>() {
                }.getType();
                clothesModels = new Gson().fromJson(data.getStringExtra("clothesModels"), listType);
                if (clothesModels.size() > 0){
                    makeOrder_clothesCount_et.setText(String.valueOf(clothesModels.size() + " ədəd"));
                }else {
                    makeOrder_clothesCount_et.setText("");
                }
                System.out.println(clothesModels);

                Gson gson = new GsonBuilder().create();
                JsonArray myArray = gson.toJsonTree(clothesModels).getAsJsonArray();
                System.out.println(myArray);
            }
        }
    }


    private void makeOrder() {
        final List<OrderClothesModel> orderClothesModels = new ArrayList<>();

        for (EditClothesModel m : clothesModels) {
            try {
                Uri uri = Uri.parse(m.imageUri);
                InputStream imageStream = getContentResolver().openInputStream(uri);
                final Bitmap bm = BitmapFactory.decodeStream(imageStream);
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 70, bytes);
                byte[] b = bytes.toByteArray();
                String s = Base64.encodeToString(b, Base64.DEFAULT);

                OrderClothesModel orderClothesModel = new OrderClothesModel();
                orderClothesModel.clothesImageBase64 = s;
                orderClothesModel.clothesTypeId = m.clothTypeId;
                orderClothesModel.notes = m.note;
                orderClothesModels.add(orderClothesModel);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        final MakeOrderModel model = new MakeOrderModel();

        int terminalPointPos = makeOrder_terminalPoint_sp.getSelectedItemPosition();
        int orderTypePos = makeOrder_orderType_sp.getSelectedItemPosition();
        int execTypePos = makeOrder_executionType_sp.getSelectedItemPosition();
        String notes = makeOrder_note_et.getText().toString();

        model.setNumberOfClothes(clothesModels.size());
        model.setTerminalPointId(HomeActivity.terminalPoints.get(terminalPointPos).getId());
        model.setOrderTypeId(HomeActivity.orderTypes.get(orderTypePos).getId());
        model.setExecutionTypeId(HomeActivity.executionTypes.get(execTypePos).getId());
        model.setNotes(notes);
        model.setClothes(orderClothesModels);

        Api.getService()
                .makeOrder(model)
                .enqueue(new Callback<ApiResponse<Void>>() {
                    @Override
                    public void onResponse(Call<ApiResponse<Void>> call, Response<ApiResponse<Void>> response) {
                        if (response.isSuccessful()) {
                            ApiResponse<Void> body = response.body();
                            if (body.success) {
                                Thread thread = new Thread() {
                                    @Override
                                    public void run() {
                                        try {
                                            Thread.sleep(1500);
                                            MakeOrderActivity.this.finish();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                };
                                Toast.makeText(MakeOrderActivity.this, "Sizin sifarişiniz qeydə alındı", Toast.LENGTH_SHORT).show();
                                thread.start();
                                return;
                            }
                        }
                        Toast.makeText(MakeOrderActivity.this, "Request was not succesful. Code: " + response.code(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ApiResponse<Void>> call, Throwable t) {
                        Log.e(TAG, "onFailure: ", t);
                    }
                });
    }

    public void backPressedAlert() {

        if (clothesModels.isEmpty()) {
            finish();
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("Geri qayıtsanız seçdiyiniz paltarlar siyahıdan silinəcək.")
                    .setMessage("Geri qayıtmağa əminsinizmi?")
                    .setNegativeButton("Xeyr", null)
                    .setPositiveButton("Bəli", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            finish();
                        }
                    }).create().show();
        }
    }

    @Override
    public void onBackPressed() {
        backPressedAlert();
    }

    @Override
    public boolean onSupportNavigateUp() {
        backPressedAlert();
        return true;
    }
}



