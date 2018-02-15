package com.start.laundryapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.start.laundryapp.models.ClothesTypeModel;
import com.start.laundryapp.models.EditClothesModel;

public class EditClothesActivity extends AppCompatActivity {

    ImageView croppedImage;
    Spinner clothesTypesSpinner;
    EditText notes_et;
    Button cancelBtn, doneBtn;
    public ArrayAdapter<String> clothesTypesAdapter;

    EditClothesModel editClothesModel = new EditClothesModel();

    int clothTypeId;
    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_clothes_type);

        croppedImage = findViewById(R.id.croppedImage);
        clothesTypesSpinner = findViewById(R.id.clothesTypesSpinner);
        notes_et = findViewById(R.id.notes_et);
        cancelBtn = findViewById(R.id.cancelBtn);
        doneBtn = findViewById(R.id.doneBtn);

        Intent intent = getIntent();
        editClothesModel.imageUri = intent.getStringExtra("croppedImgURI");
        croppedImage.setImageURI(Uri.parse(editClothesModel.imageUri));
        clothTypeId = intent.getIntExtra("clothTypeId", -1);
        pos = intent.getIntExtra("pos", -1);
        String note = intent.getStringExtra("note");
        notes_et.setText(note);


        clothesTypesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, HomeActivity.clothesNamesAz);
        clothesTypesAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        clothesTypesSpinner.setAdapter(clothesTypesAdapter);

        for (int i = 0; i < HomeActivity.clothesNames.size(); i++) {
            ClothesTypeModel type = HomeActivity.clothesNames.get(i);
            if (type.getId() == clothTypeId) {
                clothesTypesSpinner.setSelection(i);
                break;
            }
        }

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
                editClothesModel.clothTypeId = HomeActivity.clothesNames.get(clothesTypesSpinner.getSelectedItemPosition()).getId();
                editClothesModel.clothName = HomeActivity.clothesNames.get(clothesTypesSpinner.getSelectedItemPosition()).getNameAz();
                editClothesModel.note = notes_et.getText().toString();
                Intent data = new Intent();
                data.putExtra("editClothesModel", new Gson().toJson(editClothesModel));
                data.putExtra("pos", pos);
                setResult(RESULT_OK, data);
                finish();
            }
        });


    }
}




