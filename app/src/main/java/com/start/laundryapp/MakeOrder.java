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
    private List<ExecutionTypeModel> executionTypes;
    private List<OrderTypeModel> orderTypes;
    private List<TerminalPointsModel> terminalPoints;
    private List<String> executionTypeAz;
    private List<String> orderTypeAz;
    private List<String> terminalPointsAz;
    private ArrayAdapter<String> executionTypesAdapter;
    private ArrayAdapter<String> orderTypesAdapter;
    private ArrayAdapter<String> terminalPointsAdapter;

    List<EditClothesModel> clothesModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_order);

        makeOrder_clothesCount_et = (EditText) findViewById(R.id.makeOrder_clothesCount_et);
        makeOrder_terminalPoint_sp = (Spinner) findViewById(R.id.makeOrder_terminalPoint_sp);
        makeOrder_orderType_sp = (Spinner) findViewById(R.id.makeOrder_orderType_sp);
        makeOrder_note_et = (EditText) findViewById(R.id.makeOrder_note_et);
        makeOrder_executionType_sp = (Spinner) findViewById(R.id.makeOrder_executionType_sp);
        makeOrder_btn = (Button) findViewById(R.id.makeOrder_btn);
        makeOrder_addPhoto_btn = (ImageView) findViewById(R.id.makeOrder_addPhoto_btn);

        executionTypes = new ArrayList<>();
        orderTypes = new ArrayList<>();
        terminalPoints = new ArrayList<>();
        executionTypeAz = new ArrayList<>();
        terminalPointsAz = new ArrayList<>();
        orderTypeAz = new ArrayList<>();

        terminalPointsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, terminalPointsAz);
        terminalPointsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        makeOrder_terminalPoint_sp.setAdapter(terminalPointsAdapter);

        executionTypesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, executionTypeAz);
        executionTypesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        makeOrder_executionType_sp.setAdapter(executionTypesAdapter);

        orderTypesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, orderTypeAz);
        orderTypesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        makeOrder_orderType_sp.setAdapter(orderTypesAdapter);

        getTerminalPointsData();
        getOrderTypesData();
        getExecutionTypesData();


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

    private void getTerminalPointsData() {
        Api.getService().terminalPoints().enqueue(new Callback<ApiResponse<ItemsHolder<TerminalPointsModel>>>() {
            @Override
            public void onResponse(Call<ApiResponse<ItemsHolder<TerminalPointsModel>>> call, Response<ApiResponse<ItemsHolder<TerminalPointsModel>>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<ItemsHolder<TerminalPointsModel>> body = response.body();
                    if (body.success) {
                        terminalPoints = body.result.items;

                        for (TerminalPointsModel point : terminalPoints) {
                            terminalPointsAz.add(point.getName());
                        }
                        terminalPointsAdapter.notifyDataSetChanged();
                        return;
                    }
                }

                Toast.makeText(MakeOrder.this, "Request was not succesful. Code: " + response.code(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ApiResponse<ItemsHolder<TerminalPointsModel>>> call, Throwable t) {
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

                        for (OrderTypeModel type : orderTypes) {
                            orderTypeAz.add(type.getNameAz());
                        }
                        orderTypesAdapter.notifyDataSetChanged();
                        return;
                    }
                }

                Toast.makeText(MakeOrder.this, "Request was not succesful. Code: " + response.code(), Toast.LENGTH_SHORT).show();
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

                        for (ExecutionTypeModel type : executionTypes) {
                            executionTypeAz.add(type.getNameAz());
                        }
                        executionTypesAdapter.notifyDataSetChanged();
                        return;
                    }
                }

                Toast.makeText(MakeOrder.this, "Request was not succesful. Code: " + response.code(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ApiResponse<ItemsHolder<ExecutionTypeModel>>> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });
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
        model.setTerminalPointId(terminalPoints.get(terminalPointPos).getId());
        model.setOrderTypeId(orderTypes.get(orderTypePos).getId());
        model.setExecutionTypeId(executionTypes.get(execTypePos).getId());
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
                                Toast.makeText(MakeOrder.this, "Sizin sifarişiniz qeydə alındı", Toast.LENGTH_SHORT).show();
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



