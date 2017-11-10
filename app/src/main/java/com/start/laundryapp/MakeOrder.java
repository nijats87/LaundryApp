package com.start.laundryapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
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
import com.start.laundryapp.adapters.OrdersRecyclerAdapter;
import com.start.laundryapp.models.ApiResponse;
import com.start.laundryapp.models.EditClothesModel;
import com.start.laundryapp.models.ExecutionTypeModel;
import com.start.laundryapp.models.ItemsHolder;
import com.start.laundryapp.models.MakeOrderModel;
import com.start.laundryapp.models.OrderClothesModel;
import com.start.laundryapp.models.OrderTypeModel;
import com.start.laundryapp.models.TerminalPointsModel;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MakeOrder extends AppCompatActivity {

    private static final String TAG = MakeOrder.class.getSimpleName();
    EditText makeOrder_clothesCount_et, makeOrder_note_et;
    Button makeOrder_btn;
    ImageView makeOrder_addPhoto_btn;
    Spinner makeOrder_terminalPoint_sp, makeOrder_orderType_sp, makeOrder_executionType_sp;

    private List<EditClothesModel> clothesModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_order);

        makeOrder_clothesCount_et = findViewById(R.id.makeOrder_clothesCount_et);
        makeOrder_terminalPoint_sp = findViewById(R.id.makeOrder_terminalPoint_sp);
        makeOrder_orderType_sp = findViewById(R.id.makeOrder_orderType_sp);
        makeOrder_note_et = findViewById(R.id.makeOrder_note_et);
        makeOrder_executionType_sp = findViewById(R.id.makeOrder_executionType_sp);
        makeOrder_btn = findViewById(R.id.makeOrder_btn);
        makeOrder_addPhoto_btn = findViewById(R.id.makeOrder_addPhoto_btn);

        clothesModels = new ArrayList<>();

        ArrayAdapter<String> terminalPointsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Home.terminalPointsAz);
        terminalPointsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        makeOrder_terminalPoint_sp.setAdapter(terminalPointsAdapter);

        ArrayAdapter<String> executionTypesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Home.executionTypeAz);
        executionTypesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        makeOrder_executionType_sp.setAdapter(executionTypesAdapter);

        ArrayAdapter<String> orderTypesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Home.orderTypeAz);
        orderTypesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        makeOrder_orderType_sp.setAdapter(orderTypesAdapter);


        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MakeOrder.this, ClothesActivity.class);
                intent.putExtra("clothesModels", new Gson().toJson(clothesModels));
                startActivityForResult(intent, clothesCode);
            }
        };

        makeOrder_clothesCount_et.setOnClickListener(listener);
        makeOrder_addPhoto_btn.setOnClickListener(listener);
        makeOrder_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeOrder();
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
                makeOrder_clothesCount_et.setText(String.valueOf(clothesModels.size()));
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
        model.setTerminalPointId(Home.terminalPoints.get(terminalPointPos).getId());
        model.setOrderTypeId(Home.orderTypes.get(orderTypePos).getId());
        model.setExecutionTypeId(Home.executionTypes.get(execTypePos).getId());
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
                                            MakeOrder.this.finish();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                };
                                Toast.makeText(MakeOrder.this, "Sizin sifarişiniz qeydə alındı", Toast.LENGTH_SHORT).show();
                                thread.start();
                                return;
                            }
                        }
                        Toast.makeText(MakeOrder.this, "Request was not succesful. Code: " + response.code(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ApiResponse<Void>> call, Throwable t) {
                        Log.e(TAG, "onFailure: ", t);
                    }
                });

    }

    @Override
    public void onBackPressed() {
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

}



