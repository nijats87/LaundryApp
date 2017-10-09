package com.start.laundryapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.start.laundryapp.models.EditClothesModel;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class ClothesActivity extends AppCompatActivity {

    ClothesRecyclerAdapter adapter = new ClothesRecyclerAdapter(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothes);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
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
    public static int clothesEditedCode = 2323;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == clothesAddedCode) {
            if (resultCode == RESULT_OK) {
                EditClothesModel model = new Gson().fromJson(data.getStringExtra("editClothesModel"), EditClothesModel.class);
                adapter.data.add(model);
                adapter.notifyDataSetChanged();
                View b = findViewById(R.id.confirmClothesBtn);
                b.setVisibility(View.VISIBLE);
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Tapped cancel", Toast.LENGTH_SHORT).show();
            }
        }

        if (requestCode == clothesEditedCode) {
            if (resultCode == RESULT_OK) {
                EditClothesModel model = new Gson().fromJson(data.getStringExtra("editClothesModel"), EditClothesModel.class);
                int pos = data.getIntExtra("pos", -1);
                adapter.data.set(pos, model);
                adapter.notifyDataSetChanged();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Tapped cancel", Toast.LENGTH_SHORT).show();
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
                Exception error = result.getError();
            }
        }

    }

}
