package com.start.laundryapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.start.laundryapp.models.ApiResponse;
import com.start.laundryapp.models.EditClothesModel;
import com.start.laundryapp.models.ClothesTypeModel;
import com.start.laundryapp.models.ItemsHolder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class EditClothesActivity extends AppCompatActivity {

    ImageView croppedImage;
    Spinner clothesTypesSpinner;
    EditText notes_et;
    Button cancelBtn, doneBtn;
    private List<ClothesTypeModel> clothesNames;
    private ArrayAdapter<String> clothesTypesAdapter;
    private List<String> clothesTypeAz;

    EditClothesModel editClothesModel = new EditClothesModel();

    int clothTypeId;
    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_clothes_type);

        getClothesTypesData();
        croppedImage = findViewById(R.id.croppedImage);
        clothesTypesSpinner = findViewById(R.id.clothesTypesSpinner);
        notes_et = findViewById(R.id.notes_et);
        cancelBtn = findViewById(R.id.cancelBtn);
        doneBtn = findViewById(R.id.doneBtn);

        clothesNames = new ArrayList<>();
        clothesTypeAz = new ArrayList<>();

        Intent intent = getIntent();
        editClothesModel.imageUri = intent.getStringExtra("croppedImgURI");
        croppedImage.setImageURI(Uri.parse(editClothesModel.imageUri));
        clothTypeId = intent.getIntExtra("clothTypeId", -1);
        pos = intent.getIntExtra("pos", -1);
        String note = intent.getStringExtra("note");
        notes_et.setText(note);


        clothesTypesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, clothesTypeAz);
        clothesTypesAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        clothesTypesSpinner.setAdapter(clothesTypesAdapter);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                setResult(RESULT_CANCELED, data);
                finish();
            }
        });

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: validate
                editClothesModel.clothTypeId = clothesNames.get(clothesTypesSpinner.getSelectedItemPosition()).getId();
                editClothesModel.clothName = clothesNames.get(clothesTypesSpinner.getSelectedItemPosition()).getNameAz();
                editClothesModel.note = notes_et.getText().toString();
                Intent data = new Intent();
                data.putExtra("editClothesModel", new Gson().toJson(editClothesModel));
                data.putExtra("pos", pos);
                setResult(RESULT_OK, data);
                finish();
            }
        });


    }

    private void getClothesTypesData() {
        Api.getService().clothesTypes().enqueue(new Callback<ApiResponse<ItemsHolder<ClothesTypeModel>>>() {
            @Override
            public void onResponse(Call<ApiResponse<ItemsHolder<ClothesTypeModel>>> call, retrofit2.Response<ApiResponse<ItemsHolder<ClothesTypeModel>>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<ItemsHolder<ClothesTypeModel>> body = response.body();
                    if (body.success) {
                        clothesNames = body.result.items;

                        int pos = -1;
                        for (int i = 0; i < clothesNames.size(); i++) {
                            ClothesTypeModel type = clothesNames.get(i);
                            clothesTypeAz.add(type.getNameAz());
                            if (type.getId() == clothTypeId) {
                                pos = i;
                            }
                        }
                        clothesTypesAdapter.notifyDataSetChanged();
                        if (pos > -1) {
                            clothesTypesSpinner.setSelection(pos);
                        }
                        return;
                    }
                }

                Toast.makeText(EditClothesActivity.this, "Request was not succesful. Code: " + response.code(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ApiResponse<ItemsHolder<ClothesTypeModel>>> call, Throwable t) {
                Log.e(EditClothesActivity.class.getSimpleName(), "onFailure: ", t);
            }
        });
    }


}



