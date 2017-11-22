package com.start.laundryapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.start.laundryapp.adapters.BaseRecyclerAdapter;
import com.start.laundryapp.adapters.ClothesRecyclerAdapter;
import com.start.laundryapp.models.EditClothesModel;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ClothesActivity extends AppCompatActivity {

    ClothesRecyclerAdapter adapter = new ClothesRecyclerAdapter(this, new BaseRecyclerAdapter.OnClickListener<EditClothesModel>() {
        @Override
        public void onClick(EditClothesModel model, int position) {
            Intent in = new Intent(ClothesActivity.this, EditClothesActivity.class);
            in.putExtra("croppedImgURI", model.imageUri);
            in.putExtra("note", model.note);
            in.putExtra("clothTypeId", model.clothTypeId);
            in.putExtra("pos", position);
            ClothesActivity.this.startActivityForResult(in, clothesEditedCode);
        }
    });

    Button confirmClothesBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothes);

        Intent intent = getIntent();
        Type listType = new TypeToken<ArrayList<EditClothesModel>>(){}.getType();
        adapter.data = new Gson().fromJson(intent.getStringExtra("clothesModels"), listType);
        adapter.notifyDataSetChanged();
        if (!adapter.data.isEmpty()) {
            findViewById(R.id.confirmClothesBtn).setVisibility(View.VISIBLE);
        }

        confirmClothesBtn = findViewById(R.id.confirmClothesBtn);

        confirmClothesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResultAndFinish();
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerViewClothes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    void setResultAndFinish() {
        Intent intent = new Intent();
        intent.putExtra("clothesModels", new Gson().toJson(adapter.data));
        ClothesActivity.this.setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.clothes_activity_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_photo:
                openCamera();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openCamera() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this);
    }

    int clothesAddedCode = 12321;
    int clothesEditedCode = 2323;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == clothesAddedCode) {
            if (resultCode == RESULT_OK) {
                EditClothesModel model = new Gson().fromJson(data.getStringExtra("editClothesModel"), EditClothesModel.class);
                adapter.data.add(model);
                adapter.notifyDataSetChanged();
                View b = findViewById(R.id.confirmClothesBtn);
                b.setVisibility(View.VISIBLE);
            }
        }

        if (requestCode == clothesEditedCode) {
            if (resultCode == RESULT_OK) {
                EditClothesModel model = new Gson().fromJson(data.getStringExtra("editClothesModel"), EditClothesModel.class);
                int pos = data.getIntExtra("pos", -1);
                adapter.data.set(pos, model);
                adapter.notifyDataSetChanged();
            }
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                Intent intent = new Intent(ClothesActivity.this, EditClothesActivity.class);
                intent.putExtra("croppedImgURI", resultUri.toString());
                startActivityForResult(intent, clothesAddedCode);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }

    }




    @Override
    public void onBackPressed() {
        if (adapter.data.isEmpty()) {
            setResultAndFinish();
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("Geri qayıtsanız seçdiyiniz paltarlar siyahıdan silinəcək.")
                    .setMessage("Geri qayıtmağa əminsinizmi?")
                    .setNegativeButton("Xeyr", null)
                    .setPositiveButton("Bəli", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            adapter.data.clear();
                            setResultAndFinish();
                        }
                    }).create().show();
        }


    }


}
