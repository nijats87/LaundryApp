package com.start.laundryapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.github.chrisbanes.photoview.PhotoView;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ImageViewerForOrderClothesActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer_for_order);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String uri = intent.getStringExtra("imageUri");

        PhotoView photoView = findViewById(R.id.imageViewForOrder);

        ImageLoader.getInstance().displayImage(uri, photoView);

        Log.i("URI", uri);

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
